import React from 'react'
import {useDispatch, useSelector} from 'react-redux'
import {Link} from 'react-router-dom';

function HomeAdmin() {
    const dispatch = useDispatch();
    const adminInfo = useSelector((state) => state.admin.adminInfo);


    // useEffect(() => {
    //     dispatch(setAdminInfo(1));
    // }, [])
    // console.log(adminInfo);

    return (<div className="font-bodyFont bg-gray-100">
            <div className="w-full mt-30 xl:-mt-12 py-10">
                <div className="mx-auto h-auto grid grid-cols-2 bg-slate-400 ">
                    <div className="w-full h-full px-4 col-span-1 py-10 flex flex-col pl-12 ">
                        <span className='pl-20 text-7xl font-sans font-bold py-5'>
                            Start <br></br> manage <br></br> with <br></br> Apollo
                        </span>
                        <span className='font-titleFont text-xl pl-20'>The fastest-growing and preferred <br></br> acquisition channel for over half our <br></br> multichannel store.1</span>
                        {adminInfo ? <Link to="/admin/shop">
                            <div className='pl-20 mt-6'>
                                <button
                                    className=" pt-4 pb-4 pl-4 pr-4 text-4xl bg-amber-500 font-bodyFont font-bold hover hover:border-1 rounded-full">Start
                                </button>
                            </div>
                        </Link> : <Link to="/admincentral/register">
                            <div className='pl-20 mt-6'>
                                <button
                                    className=" pt-4 pb-4 pl-4 pr-4 text-4xl bg-amber-500 font-bodyFont font-bold hover hover:border-1 rounded-full">Sign
                                    up
                                </button>
                            </div>
                        </Link>}
                        <span className='pl-20 mt-4 font-titleFont text-xs'>$39.99 a month + admin fees</span>
                    </div>
                    <div className='col-span-1 pt-10'>
                        <img
                            src='https://m.media-amazon.com/images/G/01/sp-marketing-toolkit/guides/design/illustration/LoFi/CN2NA_lofi.png'
                            alt="hero"/>
                    </div>

                </div>
            </div>

        </div>)
}

export default HomeAdmin
