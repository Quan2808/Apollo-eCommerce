import React, { useState } from 'react'
import { useForm } from 'react-hook-form'
import HomeIcon from '@mui/icons-material/Home';
import { useDispatch, useSelector } from 'react-redux';
import { addStore, selectLoading, selectError, selectSuccess, selectStoreDetail, selectStoreAdded } from '../../../../features/variant/shopSlide';
import { Link, Navigate, useNavigate } from 'react-router-dom';
import { selectAdminDetail } from '../../../../features/admin/adminSlice'
import Swal from 'sweetalert2';




function ShopCreate() {
    const adminInfo = useSelector(selectAdminDetail);
    const store = useSelector((state) => state.shop.store);
    const loading = useSelector((state) => state.shop.loading);
    // console.log(adminInfo.id);
    const dispatch = useDispatch();
    const navigate = useNavigate();
    const [storeName, setStoreName] = useState('');
    const { register, handleSubmit } = useForm();
    console.log(register);
    // const StoreAdded = useSelector(selectStoreAdded);
    console.log(store);

    // console.log(StoreAdded);
    const onSubmit = async (data) => {
            if (adminInfo == null) {
                navigate("/signin");
            }
            dispatch(addStore({shop:data,adminId:adminInfo.id}))
            .then(() => {
                setStoreName("");
                Swal.fire({
                    title: 'Create Store In Apollo: ' + data.name,
                    showClass: {
                        popup: 'animate__animated animate__fadeInDown'
                    },
                    hideClass: {
                        popup: 'animate__animated animate__fadeOutUp'
                    }
                })
            })
                .catch((error) => {
                    alert("Tạo store không thành công");
                });
    }
    return (
        <div className="mx-auto h-auto grid grid-cols-2 bg-slate-400 ">
            <div className="w-full h-full px-4 col-span-1 flex flex-col pl-12 ">
                <span className='pl-20 text-5xl font-sans font-bold'>
                    To Begin Registering Your Store
                </span>
                <span className='font-titleFont text-xl pl-20'>"Unlock the Potential of Your Business: Seamlessly Register Your Store and Join The Growing Global Marketplace"</span>
                <div className='ml-20 mt-4 pt-4 pb-4  bg-white font-bodyFont font-bold hover hover:border-1 rounded-sm'>
                    <h1 className='text-center text-3xl'><HomeIcon className='text-yellow-300' sx={{ fontSize: 70 }} /> SELLING ON APOLLO</h1>
                    <span className='pl-20 mt-4 font-titleFont text-xs text-right'>
                        Admin Store</span>
                    <form className='p-8' onSubmit={handleSubmit(onSubmit)} >
                        <div className='w-full text-2xl font-titleFont flex flex-col h-full'>
                            <label>Enter Name</label>
                            <input className='border-2 rounded-xl pl-5' name="name" type="text" {...register('name')} />
                        </div>
                        <div className='flex justify-between mt-4'>
                            <button type="submit" className='bg-gray-300 h-full text-3xl rounded-sm border-2 hover:bg-gray-200'>Create</button>
                            <Link to={`/admin/category`}>
                                <span className='items-right text-titleFont font-bold hover:border-4 hover:bg-black hover:text-white text-3xl text-right'>NEXT</span>
                            </Link>
                            
                        </div>
                    </form>
                </div>

            </div>
            <div className='col-span-1 pt-10'>
                <img src='https://m.media-amazon.com/images/G/01/sp-marketing-toolkit/guides/design/illustration/sell-universe-3.svg' />
            </div>
        </div>
    )
}

export default ShopCreate;
