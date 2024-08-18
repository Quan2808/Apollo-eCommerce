import React from 'react'

function Home() {
  return (
    <div className='fixed top-50 w-full pr-20'>
      <h1 className='text-titleFont font-bold text-4xl mb-6'>Dashboard</h1>
      <div className='grid grid-cols-3 w-full h-200 bg-white text-xs'>
        <div className='col-span-1 border-x-2 py-8 px-3'>
          <div className='text-titleFont'>Total Sales</div>
          <div className='flex justify-between'>
            <div className='font-bold text-3xl'>$3000</div>
            <div className='flex flex-col items-right'>
              <div> Decrease 30%</div>
              <div> Comparing To 30.12.2022</div>
            </div>
          </div>

        </div>
        <div className='col-span-1 border-x-2 py-8  px-3'>
          <div className='text-titleFont'>Average order value</div>
          <div className='flex justify-between'>
            <div className='font-bold text-3xl'>3000</div>
            <div className='flex flex-col items-right'>
              <div> Increase 30%</div>
              <div> Comparing To 30.12.2022</div>
            </div>
          </div>

        </div>
        <div className='col-span-1 border-x-2 py-8  px-3'>
          <div className='text-titleFont'>Total orders</div>
          <div className='flex justify-between'>
            <div className='font-bold text-3xl'>30</div>
            <div className='flex flex-col items-right'>
              <div> Increase 30%</div>
              <div> Comparing To 30.12.2022</div>
            </div>
          </div>
        </div>
      </div>

      <div className='grid grid-cols-2 w-full h-500 mt-5 bg-gray-400 text-xs'>
        <div className='col-span-1 border-x-2 py-8 px-3'>
          <div className='text-titleFont'>Total Sales</div>
          <div className='flex justify-between'>
            <div className='font-bold text-3xl'>$3000</div>
            <div className='flex flex-col items-right'>
              <div> tang 30%</div>
              <div> Comparing To 30.12.2022</div>
            </div>
          </div>

        </div>
        <div className='col-span-1 border-x-2 py-8  px-3'>
          <div className='text-titleFont'>Recent reviews</div>
          <hr></hr>
          <div className='grid grid-cols-3'>
            <div className='col-span-1'>avatar</div>
            <div className='col-span-1'>reviews</div>
            <div className='col-span-1'>star</div>
          </div>
        </div>
      </div>


    </div>

  )
}

export default Home
