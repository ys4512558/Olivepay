/** @type {import('tailwindcss').Config} */
export default {
  content: ['./src/**/*.{js,jsx,ts,tsx}'],
  theme: {
    extend: {
      fontFamily: {
        sans: ['mainFont', 'sans-serif', 'system-ui'],
        title: ['titleFont', 'sans-serif', 'system-ui'],
      },
      colors: {
        PRIMARY: '#99BBA2',
        SECONDARY: '#C6D6B2',
        TERTIARY: '#739E93',
        DARK: '#3E646F',
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
        appear: {
          '0%': {
            opacity: 0,
            transform: 'scale(0)',
          },
          '70%': {
            opacity: 1,
            transform: 'scale(1.1)',
          },
          '100%': {
            opacity: 1,
            transform: 'scale(1)',
          },
        },
        explode: {
          '0%': { transform: 'scale(0) translate(-50%, -50%)', opacity: '1' },
          '50%': { opacity: '1' },
          '100%': {
            transform:
              'scale(1) translate(var(--x), var(--y)) rotate(var(--rotate))',
            opacity: '0',
          },
        },
      },
      animation: {
        slideUp: 'slideUp 0.5s ease-out forwards',
        appear: 'appear 0.8s forwards cubic-bezier(0.25, 0.1, 0.25, 1)',
        explode: 'explode 0.8s forwards cubic-bezier(0.25, 0.1, 0.25, 1)',
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
  plugins: [
    require('@tailwindcss/line-clamp'),
    function ({ addUtilities }) {
      const newUtilities = {
        '.scrollbar-hide': {
          'scrollbar-width': 'none',
          '-ms-overflow-style': 'none',
          '&::-webkit-scrollbar': {
            display: 'none',
          },
        },
      };
      addUtilities(newUtilities);
    },
  ],
};
