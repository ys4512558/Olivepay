/** @type {import('tailwindcss').Config} */
export default {
  content: ['./src/**/*.{js,jsx,ts,tsx}'],
  theme: {
    extend: {
      colors: {
        PRIMARY: '#99BBA2',
        SECONDARY: '#C6D6B2',
        BASE: '#AFAFAF',
        LIGHTBASE: '#DADADA',
        DARKBASE: '#6F6F6F',
        WHITE: '#FFFFFF',
        BLUE: '#3772FF',
        RED: '#DF2935',
      },
    },
  },
  plugins: [],
};
