import clsx from 'clsx';

const tw = (strings: TemplateStringsArray): string => {
  return strings.join('');
};

const BUTTON_VARIANTS = {
  primary: tw`m-2 my-4 bg-blue-500 text-black hover:bg-blue-600`,
  secondary: tw`bg-gray-500 text-white hover:bg-gray-600`,
  danger: tw`bg-red-500 text-white hover:bg-red-600`,
};

function App() {
  return (
    <>
      <p className="m-2 w-1/2 bg-red-200 p-4 py-2 text-center text-4xl">
        설정 완료!
      </p>
      <button className={clsx(BUTTON_VARIANTS.primary, 'w-1/2')}>되나</button>
    </>
  );
}

export default App;
