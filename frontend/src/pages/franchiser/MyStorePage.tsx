import { useAtom } from 'jotai';

import { Layout } from '../../component/common';
import { FranchiseInfo } from '../../component/franchise';
import { franchiseAtom } from '../../atoms';

const MyStorePage = () => {
  const [store, setStore] = useAtom(franchiseAtom);
  const franchiseId = 1;
  return (
    <Layout>
      <main>
        <section className="my-8">
          <FranchiseInfo
            franchiseName={store.franchiseName}
            category={store.category}
            likes={store.likes}
            address={store.address}
            reviewCount={store.reviews.length}
          />
        </section>
      </main>
    </Layout>
  );
};

export default MyStorePage;
