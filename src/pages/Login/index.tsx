import { useState, useRef, useEffect } from 'react'
import { useNavigate } from 'react-router'
import { authApi } from '@/api'
import { useAuthStore } from '@/store/auth'
import './style.css'

export default function Login() {
  const [mode, setMode] = useState<'login' | 'register'>('login')
  const [username, setUsername] = useState('')
  const [password, setPassword] = useState('')
  const [nickname, setNickname] = useState('')
  const [error, setError] = useState('')
  const [loading, setLoading] = useState(false)
  const canvasRef = useRef<HTMLCanvasElement>(null)
  const navigate = useNavigate()
  const { login } = useAuthStore()

  // 粒子背景动画
  useEffect(() => {
    const canvas = canvasRef.current
    if (!canvas) return
    const ctx = canvas.getContext('2d')!
    let animId: number
    let particles: { x: number; y: number; vx: number; vy: number; size: number; alpha: number }[] = []
    let lines: { x1: number; y1: number; x2: number; y2: number; alpha: number }[] = []

    const resize = () => {
      canvas.width = window.innerWidth
      canvas.height = window.innerHeight
    }
    resize()
    window.addEventListener('resize', resize)

    // 初始化粒子
    const count = 80
    for (let i = 0; i < count; i++) {
      particles.push({
        x: Math.random() * canvas.width,
        y: Math.random() * canvas.height,
        vx: (Math.random() - 0.5) * 0.5,
        vy: (Math.random() - 0.5) * 0.5,
        size: Math.random() * 2 + 0.5,
        alpha: Math.random() * 0.5 + 0.2,
      })
    }

    // 六边形网格
    const hexSize = 60
    const hexPoints = (cx: number, cy: number, s: number) => {
      const pts = []
      for (let i = 0; i < 6; i++) {
        const angle = (Math.PI / 3) * i - Math.PI / 6
        pts.push({ x: cx + s * Math.cos(angle), y: cy + s * Math.sin(angle) })
      }
      return pts
    }

    let time = 0
    const animate = () => {
      time += 0.005
      ctx.clearRect(0, 0, canvas.width, canvas.height)

      // 绘制六边形网格（淡）
      ctx.strokeStyle = 'rgba(64, 156, 255, 0.04)'
      ctx.lineWidth = 0.5
      for (let row = -1; row < canvas.height / (hexSize * 1.5) + 2; row++) {
        for (let col = -1; col < canvas.width / (hexSize * Math.sqrt(3)) + 2; col++) {
          const cx = col * hexSize * Math.sqrt(3) + (row % 2) * hexSize * Math.sqrt(3) / 2
          const cy = row * hexSize * 1.5
          const pts = hexPoints(cx, cy, hexSize)
          const pulse = Math.sin(time * 2 + col * 0.3 + row * 0.5) * 0.5 + 0.5
          ctx.strokeStyle = `rgba(64, 156, 255, ${0.02 + pulse * 0.03})`
          ctx.beginPath()
          ctx.moveTo(pts[0].x, pts[0].y)
          for (let i = 1; i < 6; i++) ctx.lineTo(pts[i].x, pts[i].y)
          ctx.closePath()
          ctx.stroke()
        }
      }

      // 更新和绘制粒子
      particles.forEach((p) => {
        p.x += p.vx
        p.y += p.vy
        if (p.x < 0 || p.x > canvas.width) p.vx *= -1
        if (p.y < 0 || p.y > canvas.height) p.vy *= -1

        const pulse = Math.sin(time * 3 + p.x * 0.01) * 0.3 + 0.7
        ctx.beginPath()
        ctx.arc(p.x, p.y, p.size, 0, Math.PI * 2)
        ctx.fillStyle = `rgba(64, 156, 255, ${p.alpha * pulse})`
        ctx.fill()

        // 粒子发光
        ctx.beginPath()
        ctx.arc(p.x, p.y, p.size * 3, 0, Math.PI * 2)
        ctx.fillStyle = `rgba(64, 156, 255, ${p.alpha * pulse * 0.1})`
        ctx.fill()
      })

      // 粒子连线
      ctx.lineWidth = 0.5
      for (let i = 0; i < particles.length; i++) {
        for (let j = i + 1; j < particles.length; j++) {
          const dx = particles[i].x - particles[j].x
          const dy = particles[i].y - particles[j].y
          const dist = Math.sqrt(dx * dx + dy * dy)
          if (dist < 150) {
            const alpha = (1 - dist / 150) * 0.15
            ctx.strokeStyle = `rgba(64, 156, 255, ${alpha})`
            ctx.beginPath()
            ctx.moveTo(particles[i].x, particles[i].y)
            ctx.lineTo(particles[j].x, particles[j].y)
            ctx.stroke()
          }
        }
      }

      // 扫描线
      const scanY = (time * 80) % canvas.height
      const gradient = ctx.createLinearGradient(0, scanY - 40, 0, scanY + 40)
      gradient.addColorStop(0, 'rgba(64, 156, 255, 0)')
      gradient.addColorStop(0.5, 'rgba(64, 156, 255, 0.06)')
      gradient.addColorStop(1, 'rgba(64, 156, 255, 0)')
      ctx.fillStyle = gradient
      ctx.fillRect(0, scanY - 40, canvas.width, 80)

      animId = requestAnimationFrame(animate)
    }
    animate()

    return () => {
      cancelAnimationFrame(animId)
      window.removeEventListener('resize', resize)
    }
  }, [])

  const handleSubmit = async () => {
    if (!username || !password) {
      setError('请输入用户名和密码')
      return
    }
    setLoading(true)
    setError('')
    try {
      if (mode === 'register') {
        await authApi.register(username, password, nickname || username)
      }
      const res: any = await authApi.login(username, password)
      login(res.data.token, res.data.user)
      navigate('/')
    } catch (err: any) {
      setError(err.message || '操作失败')
    } finally {
      setLoading(false)
    }
  }

  const handleKeyDown = (e: React.KeyboardEvent) => {
    if (e.key === 'Enter') handleSubmit()
  }

  return (
    <div className="login-container">
      <canvas ref={canvasRef} className="login-canvas" />

      {/* 角落装饰 */}
      <div className="corner corner-tl" />
      <div className="corner corner-tr" />
      <div className="corner corner-bl" />
      <div className="corner corner-br" />

      {/* 标题 */}
      <div className="login-header">
        <div className="login-title-glow" />
        <h1 className="login-title">甘肃省数据可视化大屏</h1>
        <p className="login-subtitle">GANSU DATA VISUALIZATION PLATFORM</p>
        <div className="title-line" />
      </div>

      {/* 登录卡片 */}
      <div className="login-card">
        <div className="card-border-top" />
        <div className="card-border-bottom" />

        {/* 标签切换 */}
        <div className="login-tabs">
          <button
            className={`login-tab ${mode === 'login' ? 'active' : ''}`}
            onClick={() => { setMode('login'); setError('') }}
          >
            <span className="tab-icon">◈</span> 登 录
          </button>
          <button
            className={`login-tab ${mode === 'register' ? 'active' : ''}`}
            onClick={() => { setMode('register'); setError('') }}
          >
            <span className="tab-icon">◇</span> 注 册
          </button>
        </div>

        {/* 表单 */}
        <div className="login-form">
          {mode === 'register' && (
            <div className="input-group">
              <label className="input-label">NICKNAME</label>
              <input
                className="login-input"
                placeholder="昵称（选填）"
                value={nickname}
                onChange={(e) => setNickname(e.target.value)}
              />
            </div>
          )}

          <div className="input-group">
            <label className="input-label">USERNAME</label>
            <input
              className="login-input"
              placeholder="请输入用户名"
              value={username}
              onChange={(e) => setUsername(e.target.value)}
              onKeyDown={handleKeyDown}
            />
            <div className="input-line" />
          </div>

          <div className="input-group">
            <label className="input-label">PASSWORD</label>
            <input
              type="password"
              className="login-input"
              placeholder="请输入密码"
              value={password}
              onChange={(e) => setPassword(e.target.value)}
              onKeyDown={handleKeyDown}
            />
            <div className="input-line" />
          </div>

          {error && <div className="login-error">⚠ {error}</div>}

          <button className="login-btn" onClick={handleSubmit} disabled={loading}>
            <span className="btn-bg" />
            <span className="btn-text">
              {loading ? (
                <span className="btn-loading">
                  <span className="spinner" /> 处理中...
                </span>
              ) : mode === 'login' ? (
                '登 录 系 统'
              ) : (
                '注 册 账 号'
              )}
            </span>
          </button>
        </div>

        {/* 底部信息 */}
        <div className="login-footer">
          <span className="footer-dot" />
          <span>默认账号: admin / admin123</span>
          <span className="footer-dot" />
        </div>
      </div>

      {/* 底部版权 */}
      <div className="copyright">
        © 2026 甘肃省数据可视化大屏系统 · SC-DataV
      </div>
    </div>
  )
}
