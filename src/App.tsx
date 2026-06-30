import { lazy, useLayoutEffect, useRef } from "react";
import { Navigate, Route, Routes, useLocation } from "react-router";
import { gsap } from "gsap";
import { useAuthStore } from "./store/auth";
import Demo0 from "./pages/Demo0";
import Demo1 from "./pages/Demo1";
import Demo2 from "./pages/Demo2";
import Demo3 from "./pages/Demo3";

const Index = lazy(() => import("./pages/Index/index"));
const Login = lazy(() => import("./pages/Login/index"));

function ProtectedRoute({ children }: { children: React.ReactNode }) {
  const { isAuthenticated } = useAuthStore();
  if (!isAuthenticated) {
    return <Navigate to="/login" replace />;
  }
  return <>{children}</>;
}

function App() {
  const location = useLocation();
  const containerRef = useRef<HTMLDivElement>(null);

  useLayoutEffect(() => {
    const ctx = gsap.context(() => {
      gsap.fromTo(
        containerRef.current,
        { autoAlpha: 0 },
        {
          autoAlpha: 1,
          duration: 0.6,
          ease: "power3.out",
        }
      );
    }, containerRef);

    return () => ctx.revert();
  }, [location.key]);

  return (
    <div ref={containerRef} style={{ willChange: "transform, opacity" }}>
      <Routes>
        <Route path="/login" element={<Login />} />
        <Route
          path="/"
          element={
            <ProtectedRoute>
              <Index />
            </ProtectedRoute>
          }
        />
        <Route
          path="/demo0"
          element={
            <ProtectedRoute>
              <Demo0 />
            </ProtectedRoute>
          }
        />
        <Route
          path="/demo1"
          element={
            <ProtectedRoute>
              <Demo1 />
            </ProtectedRoute>
          }
        />
        <Route
          path="/demo2"
          element={
            <ProtectedRoute>
              <Demo2 />
            </ProtectedRoute>
          }
        />
        <Route
          path="/demo3"
          element={
            <ProtectedRoute>
              <Demo3 />
            </ProtectedRoute>
          }
        />
      </Routes>
    </div>
  );
}

export default App;
