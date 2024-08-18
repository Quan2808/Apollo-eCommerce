import React from 'react'
import { Link, NavLink } from 'react-router-dom'


function NavBar() {
    return (
        <div className='grid grid-cols-8 h-10 bt-4'>
            <div className='col-span-1'></div>
            <div className='col-span-6 text-titleFont'>
                <div className='grid grid-cols-8'>
                    <NavLink exact activeStyle={{
                        backgroundColor: 'white',
                        color: 'red'
                    }} to="/admin/shop" className="text-center h-10 pt-2 text-black text-sm hover:text-stone-700 hover:bg-slate-300 hover:border-2 font-bold text-titleFont">
                        <div className='col-span-1'>Store</div>
                    </NavLink>
                    <NavLink to="/admin/category" className= " text-center h-10 pt-2 text-black text-sm  hover:text-stone-700 hover:bg-slate-300 hover:border-2 font-bold text-titleFont">
                        <div className='col-span-1'>Category</div>
                    </NavLink>
                    <NavLink to="/admin/product" className="text-center h-10 pt-2 text-black text-sm  hover:text-stone-700 hover:bg-slate-300 hover:border-2 font-bold text-titleFont">
                        <div className='col-span-1'>Product Vitals</div>
                    </NavLink>
                    <NavLink to="/admin/variant" className="text-center h-10 pt-2 text-black text-sm  hover:text-stone-700 hover:bg-slate-300 hover:border-2 font-bold text-titleFont">
                        <div className='col-span-1'>Variants</div>
                    </NavLink>
                    <NavLink to="/admin/attributes" className="text-center h-10 pt-2 text-black text-sm  hover:text-stone-700 hover:bg-slate-300 hover:border-2 font-bold text-titleFont">
                        <div className='col-span-1'>Offers</div>
                    </NavLink>
                    <NavLink to="/admin/images" className="text-center h-10 pt-2 text-black text-sm  hover:text-stone-700 hover:bg-slate-300 hover:border-2 font-bold text-titleFont">
                        <div className='col-span-1'>Images</div>
                    </NavLink>
                    <NavLink to="/dashboard" className="text-center h-10 pt-2 text-black text-sm  hover:text-stone-700 hover:bg-slate-300 hover:border-2 font-bold text-titleFont">
                        <div className='col-span-1'>Go to Dashboard</div>
                    </NavLink>
                    <NavLink to="/dashboard" className="text-center h-10 pt-2 text-black text-sm  hover:text-stone-700 hover:bg-slate-300 hover:border-2 font-bold text-titleFont">
                        <div className='col-span-1'>More Details</div>
                    </NavLink>
                    

                </div>
            </div>
            <div className='col-span-1'></div>


        </div>
    )
}

export default NavBar
