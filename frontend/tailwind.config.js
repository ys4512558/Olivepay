/** @type {import('tailwindcss').Config} */
export default {
  content: ['./src/**/*.{js,jsx,ts,tsx}'],
  theme: {
    extend: {
      colors: {
        PRIMARY: '#99BBA2',
        SECONDARY: '#C6D6B2',
        TERTIARY: '#739E93',
        BASE: '#AFAFAF',
        LIGHTBASE: '#DADADA',
        DARKBASE: '#6F6F6F',
        RED: '#DF2935',
        BLUE: '#3772FF',
        YELLOW: '#FDCA40',
      },
      keyframes: {
        slideUp: {
          '0%': { opacity: '0', transform: 'translateY(20px)' },
          '100%': { opacity: '1', transform: 'translateY(0)' },
        },
      },
      animation: {
        slideUp: 'slideUp 0.5s ease-out forwards',
      },
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
  plugins: [require('@tailwindcss/line-clamp')],
};
