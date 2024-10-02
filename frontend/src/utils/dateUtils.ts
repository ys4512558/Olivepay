export const getCurrentDate = (): string => {
  const date = new Date();
  const year = date.getFullYear();
  const month = String(date.getMonth() + 1).padStart(2, '0');
  const day = String(date.getDate()).padStart(2, '0');
  return `${year}년 ${month}월 ${day}일`;
};

export const groupByDate = (data: paymentList): Record<string, paymentList> => {
  return data.reduce<Record<string, paymentList>>((acc, el) => {
    const { createdAt } = el;
    if (!acc[createdAt]) {
      acc[createdAt] = [];
    }
    acc[createdAt].push(el);
    return acc;
  }, {});
};

export function formatDate(isoDateString: string) {
  const date = new Date(isoDateString);
  return date.toLocaleString('ko-KR', {
    year: 'numeric',
    month: 'long',
    day: 'numeric',
    hour: 'numeric',
    minute: 'numeric',
    second: 'numeric',
    hour12: true,
  });
}
