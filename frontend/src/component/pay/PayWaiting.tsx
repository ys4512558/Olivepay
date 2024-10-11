import TypeIt from 'typeit-react';

const PayWaiting = () => {
  return (
    <div className="flex flex-col items-center justify-center">
      <div className="loader mt-20 flex aspect-square w-12 animate-spin items-center justify-center rounded-full border-t-2 border-yellow-500 bg-yellow-300 text-yellow-700">
        $
      </div>
      <div className="mt-8 flex">
        <p className="text-xl font-semibold">결제 중</p>
        <p className="text-xl font-semibold">
          <TypeIt
            options={{
              speed: 150,
              waitUntilVisible: true,
              loop: true,
            }}
            getBeforeInit={(instance) => {
              instance
                .type('.')
                .pause(500)
                .type('.')
                .pause(500)
                .type('.')
                .pause(500)
                .delete(1)
                .pause(500)
                .delete(1)
                .pause(500)
                .go();
              return instance;
            }}
          />
        </p>
      </div>
    </div>
  );
};

export default PayWaiting;
