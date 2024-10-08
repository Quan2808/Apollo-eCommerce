import React from 'react'
import FooterMiddleList from './FooterMiddleList';
import { middleList } from '../../../constants';
import { logo, vnFlag } from '../../../assets';

function FooterMiddle() {
  return (
    <div className="w-full bg-amazon_light text-white">
      {/* Top start */}
      <div className="w-full border-b-[1px] border-gray-500 p-10">
        <div className="max-w-5xl mx-auto text-gray-30">
          <div className="w-full grid grid-cols-1 md:grid-cols-2 lgl:grid-cols-4
          gap-6 md:place-items-center md:items-start">
            {
                middleList.map(item =>(
                    <FooterMiddleList 
                    key={item.id}
                    title={item.title}
                    listItem={item.listItem}
                    />
                ))
            }
          </div>
        </div>
      </div>
      {/* Top end */}

      {/* Bottom start */}
      <div className='w-full flex gap-6 items-center justify-center py-6'>
            <div>
                <img className='w-20 pt-3' src={logo} alt="logo"></img>
            </div>
            <div className='flex gap-2'>
                <p className='flex gap-1 items-center justify-center border border-gray-500
                hover:border-amazon_yellow cursor-pointer duration-200 px-2 py-1'>English</p>
            </div>
            {/*<div className='flex gap-1 items-center justify-center border*/}
            {/* border-gray-500 hover:border-amazon_yellow cursor-pointer duration-200 px-2 py-1'> */}
            {/*<img className='w-6' src={vnFlag} alt='flagImg'></img>*/}
            {/*<p>Vietnam</p>*/}
            {/*</div>*/}
      </div>
      {/* Bottom end */}
    </div>
  );
}

export default FooterMiddle