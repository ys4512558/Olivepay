import { useState } from 'react';

// 뉴스 목록 배열
const NewList = [
  {
    title: `방학한 지가 언젠데..아동급식카드 한도액 '늑장 상향' 아쉬움`,
    link: 'http://www.jibs.co.kr/news/articles/articlesDetail/40600?feed=na',
  },
  {
    title: `결식아동은 한 끼 9000원이면 되나요?`,
    link: 'https://www.kihoilbo.co.kr/news/articleView.html?idxno=1100226',
  },
  {
    title: `‘아동급식카드’로 술집·유흥업소 결제...? 제주, 부적합 가맹점 1000여개소 제외`,
    link: 'https://www.segye.com/newsView/20240411518495',
  },
];

const News = ({ closeModal }) => {
  const [isOpenList, setIsOpenList] = useState(
    Array(NewList.length).fill(false),
  );

  const handleToggle = (index: number) => {
    setIsOpenList((prevState) =>
      prevState.map((isOpen, i) => (i === index ? !isOpen : isOpen)),
    );
  };

  return (
    <main>
      <p className="text-lg font-bold">📰 기사 목록</p>
      <section className="flex flex-col gap-y-4 py-10 text-sm">
        {NewList.map((newsItem, index) => (
          <figure
            className="border-b border-gray-300 pb-4"
            key={index}
            title={`기사${index + 1}`}
          >
            <p
              onClick={() => handleToggle(index)}
              className="cursor-pointer text-base font-semibold text-DARKBASE"
            >
              {isOpenList[index] ? '' : ''} {newsItem.title}
            </p>
            {isOpenList[index] && (
              <a
                href={newsItem.link}
                target="_blank"
                rel="noopener noreferrer"
                className="mt-2 block text-blue-500 underline"
              >
                기사 보러가기
              </a>
            )}
          </figure>
        ))}
      </section>
    </main>
  );
};

export default News;
