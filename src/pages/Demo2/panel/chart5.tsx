import { RadarChart, type RadarSeriesOption } from "echarts/charts";
import Chart from "@/components/chart";
import type { ComposeOption } from "echarts/core";
import {
  LegendComponent,
  TooltipComponent,
  type LegendComponentOption,
  type TooltipComponentOption,
} from "echarts/components";

type PieOption = ComposeOption<
  RadarSeriesOption | TooltipComponentOption | LegendComponentOption
>;

const data = [280, 200, 180, 160, 110];
const indicator = [
  { name: "嘉峪关市", max: 350 },
  { name: "白银市", max: 350 },
  { name: "兰州市", max: 350 },
  { name: "酒泉市", max: 350 },
  { name: "天水市", max: 350 },
];

export default function Chart5() {
  return (
    <Chart<PieOption>
      use={[RadarChart, TooltipComponent, LegendComponent]}
      option={{
        radar: {
          center: ["50%", "50%"],
          radius: "100%",
          axisName: {
            color: "#BCDCFF",
          },
          axisNameGap: 0,
          indicator: indicator,
          splitLine: {
            show: false,
          },
          splitArea: {
            show: false,
          },
          axisLine: {
            show: false,
          },
        },
        series: [
          {
            type: "radar",
            data: [data],
            label: {
              show: true,
              formatter: "{c}",
              color: "#bdcfff",
              align: "right",
              distance: 10,
            },
            symbolSize: [6, 6],
            lineStyle: {
              width: 0,
            },
            areaStyle: {
              color: "#bdcfff",
              opacity: 0.6,
            },
          },
          {
            type: "radar",
            data: [[350, 350, 350, 350, 350, 350]],
            symbol: "none",
            lineStyle: {
              width: 0,
            },
            itemStyle: {
              color: "#4175F5",
            },
            areaStyle: {
              color: "#4175F5",
              opacity: 0.06,
            },
          },
          {
            type: "radar",
            data: [[300, 300, 300, 300, 300, 300]],
            symbol: "none",
            lineStyle: {
              width: 0,
            },
            itemStyle: {
              color: "#2C72C8",
            },
            areaStyle: {
              color: "#2C72C8",
              opacity: 0.12,
            },
          },
          {
            type: "radar",
            data: [[250, 250, 250, 250, 250, 250]],
            symbol: "none",
            lineStyle: {
              width: 0,
            },
            itemStyle: {
              color: "#3061DB",
            },
            areaStyle: {
              color: "#3061DB",
              opacity: 0.18,
            },
          },
          {
            type: "radar",
            data: [[200, 200, 200, 200, 200, 200]],
            symbol: "none",
            lineStyle: {
              width: 0,
            },
            itemStyle: {
              color: "#3061DB",
            },
            areaStyle: {
              color: "#3061DB",
              opacity: 0.19,
            },
          },
          {
            type: "radar",
            data: [[150, 150, 150, 150, 150, 150]],
            symbol: "none",
            lineStyle: {
              width: 0,
            },
            itemStyle: {
              color: "#3061DB",
            },
            areaStyle: {
              color: "#3061DB",
              opacity: 0.17,
            },
          },
          {
            type: "radar",
            data: [[100, 100, 100, 100, 100, 100]],
            symbol: "none",
            lineStyle: {
              width: 0,
            },
            itemStyle: {
              color: "#3061DB",
            },
            areaStyle: {
              color: "#3061DB",
              opacity: 0.16,
            },
          },
          {
            type: "radar",
            data: [[50, 50, 50, 50, 50, 50]],
            symbol: "none",
            lineStyle: {
              width: 0,
            },
            itemStyle: {
              color: "#3061DB",
            },
            areaStyle: {
              color: "#3061DB",
              opacity: 0.13,
            },
          },
        ],
      }}
    />
  );
}
