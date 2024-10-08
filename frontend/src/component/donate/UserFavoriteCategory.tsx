import { Bar } from 'react-chartjs-2';
import {
  Chart as ChartJS,
  CategoryScale,
  LinearScale,
  BarElement,
  Title,
  Tooltip,
  Legend,
} from 'chart.js';
import { franchiseCategory } from '../../types/franchise';

ChartJS.register(
  CategoryScale,
  LinearScale,
  BarElement,
  Title,
  Tooltip,
  Legend,
);

const UseFavoriteCategory = () => {
  const thisMonth = new Date(
    new Date().getFullYear(),
    new Date().getMonth(),
    1,
  ).toLocaleDateString('ko-KR');

  const ratio = [
    { category: 'KOREAN', count: 10 },
    { category: 'CHINESE', count: 2 },
    { category: 'JAPANESE', count: 3 },
    { category: 'WESTERN', count: 1 },
    { category: 'GENERAL', count: 6 },
    { category: 'FASTFOOD', count: 7 },
    { category: 'BAKERY', count: 13 },
    { category: 'SUPERMARKET', count: 1 },
    { category: 'OTHER', count: 3 },
  ];

  const labels = ratio.map(
    (item) =>
      franchiseCategory[item.category as keyof typeof franchiseCategory],
  );
  const data = ratio.map((item) => item.count);
  const sortedRatio = [...ratio].sort((a, b) => b.count - a.count);
  const backgroundColors = ratio.map((item) => {
    const rank = sortedRatio.findIndex((r) => r.category === item.category) + 1;
    if (rank === 1) return '#99BBA2';
    if (rank === 2 || rank === 3) return '#C6D6B2';
    return '#DADADA';
  });
  const barData = {
    labels: labels,
    datasets: [
      {
        label: 'Child Favorite Category',
        data: data,
        backgroundColor: backgroundColors,
        borderColor: 'white',
        borderWidth: 1,
      },
    ],
  };

  const options = {
    indexAxis: 'y' as const,
    maintainAspectRatio: false,
    scales: {
      x: {
        beginAtZero: true,
      },
    },
    plugins: {
      legend: {
        display: false,
      },
    },
  };
  return (
    <main>
      <p className="text-lg font-bold">📊 아동선호 카테고리</p>
      <p className="mt-2 text-right text-sm">{thisMonth} 기준</p>
      <div className="flex h-[500px] items-center justify-center py-5 text-center">
        <Bar data={barData} options={options} />
      </div>
    </main>
  );
};

export default UseFavoriteCategory;
