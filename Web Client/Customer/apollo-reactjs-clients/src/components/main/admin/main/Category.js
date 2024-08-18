import React, { useCallback, useEffect, useState } from 'react';
import { useDispatch, useSelector } from 'react-redux';
import Swal from 'sweetalert2';
import { BsDashCircleDotted } from "react-icons/bs";
import { Link, useParams } from 'react-router-dom';
import { getStore, selectStoreDetail } from '../../../../features/variant/shopSlide';
import { selectLoading, selectError, selectSuccess, getCategoryResult, getCategory } from '../../../../features/variant/categorySlide';
import { createStoreCategoryList } from '../../../../features/variant/storeCategorySlide';

function Category() {
    const categoryList = useSelector((state) => state.category.categories);
    const category = useSelector((state) => state.category.category);
    const store = useSelector((state) => state.shop.store);
    const storeCategoryList = useSelector((state) => state.storeCategory);
    const [searchQuery, setSearchQuery] = useState("");
    const [listResult, setListResult] = useState([]);
    const dispatch = useDispatch();
    const [categorySelected, setCategorySelected] = useState({});
    const [option, setOption] = useState("");
    const [optionList, setOptionList] = useState([]);
    const [errorMessage, setErrorMessage] = useState("");
    const [id, setId] = useState("");

    const handleChangeInput = (e) => {
        setSearchQuery(e.target.value);
    };

    const handleSearch = () => {
        if (searchQuery.length === 0) {
            Swal.fire({
                title: 'Please insert any keyword to start the search engine',
                showClass: { popup: 'animate__animated animate__fadeInDown' },
                hideClass: { popup: 'animate__animated animate__fadeOutUp' }
            });
        } else {
            dispatch(getCategoryResult(searchQuery));
            setListResult(categoryList);
            console.log(listResult);
        }
    };

    useEffect(() => {
        handleSearch();
    }, [searchQuery]);

    const handleSelectCategory = (id) => {
        setId(id);
        dispatch(getCategory(id));
        setCategorySelected(category);
        console.log(id);
        console.log(category);
    };

    // Thêm useEffect để kiểm tra giá trị của category khi thay đổi
    useEffect(() => {
        console.log("Category Selected: ", category);
    }, [category]);

    const handleSetOption = (e) => {
        setOption(e.target.value);
        console.log(option);
    };

    const addOption = () => {
        if (option.trim() !== "") {
            if (optionList.includes(option)) {
                setErrorMessage("This category is exist");
            } else {
                setOptionList([...optionList, option]);
                setOption("");
                setErrorMessage("");
            }
        }
    };

    const deleteOption = (index) => {
        const updateOptionList = [...optionList];
        updateOptionList.splice(index, 1);
        setOptionList(updateOptionList);
    };

    return (
        <div className="font-bodyFont bg-gray-200">
            {/* select category */}
            <div className='flex flex-col pl-20 pr-20 font-titleFont py-4'>
                <h2 className='text-4xl font-sans font-bold'>Select Category</h2>
                <span className='text-yellow-700 font-titleFont mb-6'>Selling appliance status</span>
                <div className='w-full h-full grid grid-cols-3 font-titleFont'>
                    <div className='col-span-2 flex flex-col border border-5 border-gray-300 rounded-xl text-xl p-6 py-4'>
                        <h2 className='font-bold'>List a New Product</h2>
                        <span className='text-sm'>Search Apollo Catalog First</span>
                        <div className='flex flex-row mb-4'>
                            <input className='w-full h-10 bg-slate-600 text-white border-4 rounded-xl hover:border-white text-sm p-2' type="text" placeholder="Search category..." value={searchQuery} onChange={handleChangeInput}></input>
                            <button className='text-sm bg-orange-400 text-center rounded-xl ml-4 w-24 h-10' onClick={handleSearch}>Search</button>
                        </div>
                        {category ? <div className='text-blue text-sm bg-gray-600 w-full rounded-xl pl-8 text-center'>You selected :{category.attribute}</div> : <div></div>}
                        {listResult.length > 0 ? listResult.map((ele, index) => (
                            <div key={index} className='flex justify-between p-6 text-xs text-bodyFont hover:bg-slate-300 rounded-xl'>
                                <span key={index}>{ele.attribute}</span>
                                <button className=" text-gray-600 hover:text-white hover:bg-gray-600 hover:font-bold rounded-sm" onClick={() => handleSelectCategory(ele.id)}>SELECT</button>
                            </div>
                        )) : <div className=' p-6 text-xs text-bodyFont hover:bg-slate-300 rounded-xl'>Not Found Category.Please enter again!!!</div>}
                        {category ? <Link to={`/admin/product/${category.id}`}>
                            <span className='items-right text-titleFont font-bold hover:border-4 hover:bg-black hover:text-white text-xl text-right'>NEXT</span>
                        </Link> : <></>}
                    </div>
                    <div className='col-span-1 flex flex-col m-auto p-auto text-titleFont py-4 rounded-xl border border-5 border-gray-300 h-auto'>
                        <span className="font-bold text-2xl underline items-center">Inventory</span>
                        <button className='bg-teal-600 h-5 rounded-sm w-40 mb-3 text-white'>Active Listing (9)</button>
                        <button className='bg-teal-600 h-5  rounded-sm w-40 text-white'>Inactive Listing (10)</button>
                    </div>
                </div>
            </div>
            {/* create List store category */}
            <div className='flex flex-col pl-20 pr-20 font-titleFont py-4'>
                <h2 className='text-4xl font-sans font-bold'>Create Store Category List</h2>
                <span className='text-yellow-700 font-titleFont mb-6'>Selling appliance status</span>
                <div className='w-full h-full grid grid-cols-3 font-titleFont'>
                    <div className='col-span-2 flex flex-col border border-5 border-gray-300 rounded-xl text-xl p-6 py-4'>
                        <h2 className='font-bold'>List a New Product</h2>
                        <span className='text-sm'>Create Store Category List</span>
                        <div className='flex'>
                            <label> Enter new category:</label>
                            <input onChange={handleSetOption} type="text" placeholder='Enter the option' value={option} className='ml-2 pl-2 rounded-xl bg-gray-500 text-yellow-50 hover:bg-slate-800 border-2 mr-8' ></input>
                            <button className="rounded-full bg-teal-600 h-10 w-10 " onClick={addOption}>+</button>
                        </div>
                        {errorMessage && <p className='text-gray-500 text-titleFont text-sm'>{errorMessage}</p>}
                        <hr></hr>
                        <div className='flex h-8 w-full px-2'>
                            {optionList.map((option, index) => (
                                <div className='flex items-baseline justify-start  text-sm border-2 bg-slate-950 text-white text-titleFont hover:bg-slate-600 rounded-xl px-1 w-full h-full' key={index}>
                                    <BsDashCircleDotted onClick={() => deleteOption(index)} />
                                    <span className='text-xl w-full h-full'>{option}</span>
                                </div>
                            ))}
                        </div>
                        <p className='text-xl font-bold'>Store Category is selected: {optionList.join('-')}</p>
                        <Link to="/admin/product"><button className='h-10 bg-teal-600 rounded-xl text-titleFont' onClick={() => { dispatch(createStoreCategoryList({ categoryList: optionList, storeId: store.id })) }}>Choose</button></Link>
                    </div>
                    <div className='col-span-1 flex flex-col m-auto p-auto text-titleFont py-4 rounded-xl border border-5 border-gray-300 h-auto'>
                        <span className="font-bold text-2xl underline items-center">Inventory</span>
                        <button className='bg-teal-600 h-5 rounded-sm w-40 mb-3 text-white'>Active Listing (9)</button>
                        <button className='bg-teal-600 h-5  rounded-sm w-40 text-white'>Inactive Listing (10)</button>
                    </div>
                </div>
            </div>
        </div>
    )
}

export default Category;
