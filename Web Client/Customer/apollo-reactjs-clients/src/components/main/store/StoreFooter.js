import React from 'react'

export default function StoreFooter() {
  const goToTop = () => {
    window.scrollTo({
      top: 0,
      behavior: "smooth",
    });
  };

  return (
    <div className="font-shopFont bg-white">
      <div className="w-full h-full text-center pt-4 text-3xl font-medium text-gray-800">
        Share
      </div>
      <div className="w-full h-full text-center pt-4 text-xl font-light text-gray-600">
        Share this page with your friends
      </div>
      <div className="flex flex-cols justify-center items-center gap-8 pt-4">
        <div className="w-[60px] h-[60px] hover:cursor-pointer">
          <img src="https://m.media-amazon.com/images/G/01/AmazonStores/facebook._CB485941703_.png"></img>
        </div>
        <div className="w-[60px] h-[60px] hover:cursor-pointer">
          <img src="https://m.media-amazon.com/images/G/01/AmazonStores/twitter._CB438369374_.png"></img>
        </div>
        <div className="w-[60px] h-[60px] hover:cursor-pointer">
          <img src="https://m.media-amazon.com/images/G/01/AmazonStores/pinterest._CB485948203_.png"></img>
        </div>
      </div>
      <span onClick={() => {
        goToTop();
      }} className="w-full h-[100%] inline-block align-middle text-center text-white mt-4 text-base hover:cursor-pointer hover:bg-gray-500 font-light bg-gray-600">
        Back to top
      </span>
    </div>
  );
}
