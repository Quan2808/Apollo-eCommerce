import React from 'react'
import { Link } from 'react-router-dom'

function SideBar() {
    return (
        <div className="w-1/4 p-4 bg-gray-800 text-white text-bodyFont font-bold text-4xl col-span-1 grid grid-rows-5 grid-flow-col">
                <div className='row-soan-1'>
                    <Link to="/dashboard" className="text-white hover:text-blue-400">
                        Home
                    </Link>
                </div>
                <div className='row-span-1'>
                    <Link to="/dashboard/categories" className="text-white hover:text-blue-400">
                        Category
                    </Link>
                </div>
                <div className='row-span-1'>
                    <Link to="/dashboard/products" className="text-white hover:text-blue-400">
                        Product
                    </Link>
                </div>
                <div className='row-span-1'>
                    <Link to="/dashboard/orders" className="text-white hover:text-blue-400">
                        Orders
                    </Link>
                </div>
                <div className='row-span-1'>
                    <Link to="/dashboard/reviews" className="text-white hover:text-blue-400">
                        Reviews
                    </Link>
                </div>

            {/* <ul>
                <li>
                    <Link to="/dashboard" className="text-white hover:text-blue-400">
                        Home
                    </Link>
                </li>
                <li>
                    <Link to="/dashboard/categories" className="text-white hover:text-blue-400">
                        Category
                    </Link>
                </li>
                <li>
                    <Link to="/dashboard/products" className="text-white hover:text-blue-400">
                        Product
                    </Link>
                </li>
                <li>
                    <Link to="/dashboard/orders" className="text-white hover:text-blue-400">
                        Orders
                    </Link>
                </li>

                <li>
                    <Link to="/dashboard/reviews" className="text-white hover:text-blue-400">
                        Reviews
                    </Link>
                </li>
            </ul> */}
        </div>
    )
}

export default SideBar
