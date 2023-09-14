import React from 'react';

const announcements = [
  {
    id: 1,
    category: '[공지사항]',
    title: '개발자들 사용 전 필수공지 안내 드립니다.',
    author: '관리자',
  },
];

function Annoucement() {
  return (
    <div className="bg-white min-h-screen flex justify-center px-2 mt-40">
      <div className="relative w-full max-w-6xl">
        <div className="relative space-y-4 text-black text-2xl mb-20">공지사항</div>
        {announcements.map((announcement) => (
          <div
            key={announcement.id}
            className="p-5 bg-white rounded-lg flex items-center justify-between space-x-8 border border-gray-600 mb-10"
          >
            <div className="flex-1 flex justify-between items-center">
              <div className="text-black">{announcement.category}</div>
              <div className="text-black">{announcement.title}</div>
              <div>{announcement.author}</div>
              <a
                href={announcement.link}
                className="w-24 h-7 rounded-lg bg-sky-300 text-center border border-gray-600 hover:text-sky-600 hover:border-sky-600 cursor-pointer"
              >
                자세히보기
              </a>
            </div>
          </div>
        ))}
      </div>
    </div>
  );
}

export default Annoucement;
