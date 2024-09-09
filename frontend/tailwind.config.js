/** @type {import('tailwindcss').Config} */
export default {
  content: ['./src/**/*.{js,jsx,ts,tsx}'],
  theme: {
    extend: {
      backgroundImage: {
        DT: 'linear-gradient(45deg, #229b41, #32c93c, #a5ce14)',
        SH: 'linear-gradient(45deg, #05274b, #324962, #858c94)',
        WR: 'linear-gradient(45deg, #79b7d0, #3f8fb0, #307b99)',
        HN: 'linear-gradient(45deg, #1b765f, #1b7476, #307b99)',
        KM: 'linear-gradient(45deg, #ffb700, #ffe000, #ffb700)',
      },
    },
  },
  safelist: ['bg-DT', 'bg-SH', 'bg-WR', 'bg-HN', 'bg-KM'],
  plugins: [],
};
