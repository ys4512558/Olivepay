import Layout from './Layout';

const Loader = () => {
  return (
    <Layout className="flex items-center" hasBottomTab={false}>
      <div className="flex w-full flex-col items-center justify-center gap-4">
        <div className="flex h-20 w-20 animate-spin items-center justify-center rounded-full border-4 border-transparent border-t-PRIMARY text-4xl text-PRIMARY">
          <div className="flex h-16 w-16 animate-spin items-center justify-center rounded-full border-4 border-transparent border-t-SECONDARY text-2xl text-SECONDARY" />
        </div>
      </div>
    </Layout>
  );
};

export default Loader;
