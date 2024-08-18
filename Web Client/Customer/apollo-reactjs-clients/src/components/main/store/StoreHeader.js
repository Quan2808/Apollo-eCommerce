import React, { Fragment, useEffect, useState } from "react";
import MoreSideBar from "./MoreSideBar";
import { useDispatch, useSelector } from "react-redux";
import {
    changeBannerImage,
    changeCategory,
    changeSubCategory,
    setCategory,
    setSearchParams,
    setSearchParamsResult,
    setSearchProducts,
    setSelectedCategory,
    setSelectedCurrent,
    setSelectedSubCategory,
    setStore,
    setStoreBanner,
    toggleMoreSideBar,
} from "../../../features/adminStore/adminStoreSlice";
import HeaderBreadcrumb from "./HeaderBreadcrumb";
import { Link, useNavigate, useParams } from "react-router-dom";
import axios from "axios";

export default function StoreHeader() {
    const [scrollPosition, setScrollPosition] = useState(0);
    const searchParams = useSelector((state) => state.adminStore.searchParams);
    const storeInfo = useSelector((state) => state.adminStore.storeInfo);
    const categories = useSelector((state) => state.adminStore.categories);
    const selectedCategory = useSelector((state) => state.adminStore.selectedCategory);
    const selectedSubCategory = useSelector((state) => state.adminStore.selectedSubCategory);
    const dispatch = useDispatch();
    const navigate = useNavigate();
    const { storeId } = useParams(); // Get store ID from URL
    const [showDropDown, setShowDropDown] = useState({ deals: false, one: false, two: false });
    const [follow, setFollow] = useState(false);

    useEffect(() => {
        if (storeId) {
            fetchStoreInfo(storeId);
        }
    }, [storeId]);

    async function fetchStoreInfo(storeId) {
        await axios.get(`http://localhost:9999/api/stores/${storeId}`)
            .then((res) => {
                dispatch(setStore(res.data)); // Update the store info in the Redux store
                dispatch(setCategory(res.data.storeCategoryList)); // Update the categories
                dispatch(setStoreBanner(res.data.homeImage)); // Update the store banner
            })
            .catch((err) => {
                console.error("Error fetching store info:", err);
            });
    }

    useEffect(() => {
        window.addEventListener("scroll", handleScroll, { passive: true });
        return () => {
            window.removeEventListener("scroll", handleScroll);
        };
    }, [scrollPosition]);

    const handleScroll = () => {
        const position = window.pageYOffset;
        setScrollPosition(position);
    };

    const handleChange = (e) => {
        dispatch(setSearchParams(e.target.value));
    };

    const handleSubmit = async (e) => {
        e.preventDefault();
        navigate(`/store/${storeInfo.id}/search`);
        dispatch(setSearchParamsResult(searchParams));
        window.history.pushState(null, null, `?search_name=${searchParams}`);
        await axios
            .get(`http://localhost:9999/api/stores/${storeInfo.id}/product?name=${searchParams}`)
            .then((res) => {
                dispatch(setSearchProducts(res.data));
                console.log(res.data);
            })
            .catch((err) => {
                throw err;
            });
    };

    return (
        <Fragment>
            <nav className={scrollPosition >= 400 ? "bg-white dark:bg-gray-900 fixed w-full z-[5] top-0 left-0 border-b border-gray-200 dark:border-gray-600" : "bg-white dark:bg-gray-900 sticky w-full z-[5] top-0 left-0 border-b border-gray-200 dark:border-gray-600"}>
                <HeaderBreadcrumb />
                <nav>
                    <div className="font-shopFont max-w-screen-xl flex flex-wrap items-center justify-between mx-6 py-2">
                        <div className="grid grid-cols-2 w-[160px]">
                            <div className="w-full h-full flex items-center justify-center">
                                <p className="text-white text-lg font-bold mr-2">{storeInfo.name + ' '}</p>
                            </div>
                            <div
                                onClick={() => {
                                    setFollow(!follow);
                                }}
                                className="text-sm border border-gray-400 outline outline-gray-400 outline-1 rounded-sm text-center w-[100px] max-w-[100px] h-[30px] hover:cursor-pointer hover:bg-gray-200 py-1 max-auto"
                            >
                                {follow ? (
                                    <p style={{color: 'white'}}>✓ Following</p>
                                ) : (
                                    <p style={{color: 'white'}}>+ Follow</p>
                                )}
                            </div>
                        </div>
                        {/* Search start */}
                        <div className="flex md:order-2 w-[200px] pl-[100px]">
                            <div className="relative md:block">
                                <div className="absolute inset-y-0 left-0 flex items-center pl-3 pointer-events-none">
                                    <svg
                                        className="w-4 h-4 text-gray-500 dark:text-gray-400"
                                        aria-hidden="true"
                                        xmlns="http://www.w3.org/2000/svg"
                                        fill="none"
                                        viewBox="0 0 20 20"
                                    >
                                        <path
                                            stroke="currentColor"
                                            strokeLinecap="round"
                                            strokeLinejoin="round"
                                            strokeWidth="2"
                                            d="m19 19-4-4m0-7A7 7 0 1 1 1 8a7 7 0 0 1 14 0Z"
                                        />
                                    </svg>
                                    <span className="sr-only">Search icon</span>
                                </div>
                                <form onSubmit={handleSubmit}>
                                    <input
                                        onChange={(e) => {
                                            handleChange(e);
                                        }}
                                        name="search_name"
                                        className="block p-2 pl-10 rounded-lg text-sm border w-[240px] border-gray-300"
                                        placeholder="Search..."
                                    ></input>
                                </form>
                            </div>
                        </div>
                        {/* Search end */}
                        <div className="items-center justify-between w-full md:flex md:w-auto md:order-1 pl-[72px]" id="navbar-sticky">
                            <ul className="flex flex-col p-4 md:p-0 mt-4 font-medium border border-gray-100 rounded-lg bg-gray-50 md:flex-row md:space-x-8 md:mt-0 md:border-0 md:bg-white dark:bg-gray-800 md:dark:bg-gray-900 dark:border-gray-700 gap-8">
                                <li name="Home">
                                    <Link
                                        onClick={() => {
                                            dispatch(changeCategory(""));
                                            dispatch(changeSubCategory(""));
                                            dispatch(setStoreBanner(storeInfo.homeImage));
                                        }}
                                        className="flex items-center text-lg justify-between w-full py-2 pl-3 pr-4 text-gray-500 rounded hover:bg-gray-100 md:hover:bg-transparent md:border-0 md:hover:underline md:p-0 md:w-auto dark:text-white md:dark:hover:underline dark:focus:text-white dark:border-gray-700 dark:hover:bg-gray-700 md:dark:hover:bg-transparent"
                                        to={`/store/${storeInfo.id}`}
                                    >
                                        HOME
                                    </Link>
                                </li>
                                <li
                                    onMouseLeave={() => {
                                        setShowDropDown({ ...showDropDown, deals: false, one: false, two: false });
                                    }}
                                >
                                    {/* Deals start */}
                                    <button
                                        name="Deals"
                                        onClick={() => {
                                            setShowDropDown({ ...showDropDown, deals: !showDropDown.deals });
                                        }}
                                        className="flex items-center text-lg justify-between w-full py-2 pl-3 pr-4 text-gray-500 rounded hover:bg-gray-100 md:hover:bg-transparent md:border-0 md:hover:underline md:p-0 md:w-auto dark:text-white md:dark:hover:underline dark:focus:text-white dark:border-gray-700 dark:hover:bg-gray-700 md:dark:hover:bg-transparent"
                                    >
                                        DEALS{" "}
                                        <svg
                                            className="w-2.5 h-2.5 ml-2.5"
                                            aria-hidden="true"
                                            xmlns="http://www.w3.org/2000/svg"
                                            fill="none"
                                            viewBox="0 0 10 6"
                                        >
                                            <path
                                                stroke="currentColor"
                                                strokeLinecap="round"
                                                strokeLinejoin="round"
                                                strokeWidth="2"
                                                d="m1 1 4 4 4-4"
                                            />
                                        </svg>
                                    </button>
                                    {showDropDown.deals && (
                                        <div className="font-shopFont absolute font-normal bg-white divide-y divide-gray-100 rounded-lg shadow w-44 dark:bg-gray-700 dark:divide-gray-600">
                                            <ul className="py-2 text-sm text-gray-700 dark:text-gray-400" aria-labelledby="dropdownLargeButton">
                                                <li>
                                                    <Link
                                                        name="Deals"
                                                        to={`/store/${storeInfo.id}/deals`}
                                                        onMouseOver={(e) => {
                                                            dispatch(setSelectedCategory(e.target.name));
                                                            dispatch(changeBannerImage(storeInfo.dealsImage));
                                                        }}
                                                        onClick={() => {
                                                            dispatch(changeCategory("Deals"));
                                                            dispatch(changeSubCategory(""));
                                                            dispatch(setStoreBanner(storeInfo.dealsImage));
                                                        }}
                                                        className="font-semibold block px-4 py-2 hover:bg-gray-100 dark:hover:bg-gray-600 dark:hover:text-white"
                                                    >
                                                        Deals
                                                    </Link>
                                                </li>
                                                {categories.map((category) =>
                                                    category.parentStoreCategory === null ? (
                                                        <li key={category.id}>
                                                            <a
                                                                name={category.name}
                                                                onClick={(e) => {
                                                                    dispatch(changeCategory("Deals"));
                                                                    dispatch(changeSubCategory(e.target.name));
                                                                    dispatch(setStoreBanner(category.heroImage));
                                                                }}
                                                                className="block cursor-pointer px-4 py-2 hover:bg-gray-100 dark:hover:bg-gray-600 dark:hover:text-white"
                                                            >
                                                                {category.name}
                                                            </a>
                                                        </li>
                                                    ) : (
                                                        ""
                                                    )
                                                )}
                                            </ul>
                                        </div>
                                    )}
                                </li>
                                {/* Deals end */}
                                {categories
                                    .filter((category) => category.parentStoreCategory === null)
                                    .map((category, index) =>
                                        index <= 1 ? (
                                            <li
                                                key={category.id}
                                                onMouseLeave={() => {
                                                    setShowDropDown({ ...showDropDown, deals: false, one: false, two: false });
                                                }}
                                            >
                                                <button
                                                    onClick={() => {
                                                        dispatch(setSelectedCategory(category.name));
                                                        index === 0 ? setShowDropDown({ ...showDropDown, one: true, two: false }) : setShowDropDown({ ...showDropDown, one: false, two: true });
                                                    }}
                                                    className="flex items-center uppercase text-lg justify-between w-full py-2 pl-3 pr-4 text-gray-500 rounded hover:bg-gray-100 md:hover:bg-transparent md:border-0 md:hover:underline md:p-0 md:w-auto dark:text-white md:dark:hover:underline dark:focus:text-white dark:border-gray-700 dark:hover:bg-gray-700 md:dark:hover:bg-transparent"
                                                >
                                                    {category.name}{" "}
                                                    <svg
                                                        className="w-2.5 h-2.5 ml-2.5"
                                                        aria-hidden="true"
                                                        xmlns="http://www.w3.org/2000/svg"
                                                        fill="none"
                                                        viewBox="0 0 10 6"
                                                    >
                                                        <path
                                                            stroke="currentColor"
                                                            strokeLinecap="round"
                                                            strokeLinejoin="round"
                                                            strokeWidth="2"
                                                            d="m1 1 4 4 4-4"
                                                        />
                                                    </svg>
                                                </button>
                                                {index === 0
                                                    ? showDropDown.one && (
                                                    <div className="font-shopFont absolute font-normal bg-white divide-y divide-gray-100 rounded-lg shadow w-44 dark:bg-gray-700 dark:divide-gray-600">
                                                        <ul className="py-2 text-sm text-gray-700 dark:text-gray-400" aria-labelledby="dropdownLargeButton">
                                                            <li>
                                                                <Link
                                                                    onMouseOver={() => {
                                                                        dispatch(changeBannerImage(category.heroImage));
                                                                    }}
                                                                    onClick={() => {
                                                                        dispatch(setSelectedCurrent(category.name));
                                                                        dispatch(setStoreBanner(category.heroImage));
                                                                        dispatch(changeCategory(selectedCategory));
                                                                        dispatch(changeSubCategory(""));
                                                                    }}
                                                                    className="font-semibold block px-4 py-2 hover:bg-gray-100 dark:hover:bg-gray-600 dark:hover:text-white"
                                                                    to={`/store/${storeInfo.id}/${selectedCategory}`}
                                                                >
                                                                    {category.name}
                                                                </Link>
                                                            </li>
                                                            {categories.map((subcategory) =>
                                                                subcategory.parentStoreCategory !== null && subcategory.parentStoreCategory.name === selectedCategory ? (
                                                                    <li key={subcategory.id}>
                                                                        <Link
                                                                            name={subcategory.name}
                                                                            onMouseOver={() => {
                                                                                dispatch(setSelectedSubCategory(subcategory.name));
                                                                            }}
                                                                            onClick={(e) => {
                                                                                dispatch(setSelectedCurrent(subcategory.name));
                                                                                dispatch(setStoreBanner(subcategory.heroImage));
                                                                                dispatch(changeCategory(selectedCategory));
                                                                                dispatch(changeSubCategory(e.target.name));
                                                                            }}
                                                                            className="font-light block px-4 py-2 hover:bg-gray-100 dark:hover:bg-gray-600 dark:hover:text-white"
                                                                            to={`/store/${storeInfo.id}/${selectedCategory}/${selectedSubCategory}`}
                                                                        >
                                                                            {subcategory.name}
                                                                        </Link>
                                                                    </li>
                                                                ) : (
                                                                    ""
                                                                )
                                                            )}
                                                        </ul>
                                                    </div>
                                                )
                                                    : showDropDown.two && (
                                                    <div className="font-shopFont absolute font-normal bg-white divide-y divide-gray-100 rounded-lg shadow w-44 dark:bg-gray-700 dark:divide-gray-600">
                                                        <ul className="py-2 text-sm text-gray-700 dark:text-gray-400" aria-labelledby="dropdownLargeButton">
                                                            <li>
                                                                <Link
                                                                    onMouseOver={() => {
                                                                        dispatch(changeBannerImage(category.heroImage));
                                                                    }}
                                                                    onClick={() => {
                                                                        dispatch(setSelectedCurrent(category.name));
                                                                        dispatch(setStoreBanner(category.heroImage));
                                                                        dispatch(changeCategory(selectedCategory));
                                                                        dispatch(changeSubCategory(""));
                                                                    }}
                                                                    className="font-semibold block px-4 py-2 hover:bg-gray-100 dark:hover:bg-gray-600 dark:hover:text-white"
                                                                    to={`/store/${storeInfo.id}/${selectedCategory}`}
                                                                >
                                                                    {category.name}
                                                                </Link>
                                                            </li>
                                                            {categories.map((subcategory) =>
                                                                subcategory.parentStoreCategory !== null && subcategory.parentStoreCategory.name === selectedCategory ? (
                                                                    <li key={subcategory.id}>
                                                                        <Link
                                                                            name={subcategory.name}
                                                                            onMouseOver={() => {
                                                                                dispatch(setSelectedSubCategory(subcategory.name));
                                                                            }}
                                                                            onClick={(e) => {
                                                                                dispatch(setSelectedCurrent(subcategory.name));
                                                                                dispatch(setStoreBanner(subcategory.heroImage));
                                                                                dispatch(changeCategory(selectedCategory));
                                                                                dispatch(changeSubCategory(e.target.name));
                                                                            }}
                                                                            className="font-light block px-4 py-2 hover:bg-gray-100 dark:hover:bg-gray-600 dark:hover:text-white"
                                                                            to={`/store/${storeInfo.id}/${selectedCategory}/${selectedSubCategory}`}
                                                                        >
                                                                            {subcategory.name}
                                                                        </Link>
                                                                    </li>
                                                                ) : (
                                                                    ""
                                                                )
                                                            )}
                                                        </ul>
                                                    </div>
                                                )}
                                            </li>
                                        ) : (
                                            ""
                                        )
                                    )}
                                <li>
                                    <button
                                        onClick={() => {
                                            dispatch(toggleMoreSideBar(true));
                                        }}
                                        className="flex items-center text-lg justify-between w-full py-2 pl-3 pr-4 text-gray-500 rounded hover:bg-gray-100 md:hover:bg-transparent md:border-0 md:hover:underline md:p-0 md:w-auto dark:text-white md:dark:hover:underline dark:focus:text-white dark:border-gray-700 dark:hover:bg-gray-700 md:dark:hover:bg-transparent"
                                    >
                                        MORE{" "}
                                        <svg
                                            className="w-2.5 h-2.5 ml-2.5"
                                            aria-hidden="true"
                                            xmlns="http://www.w3.org/2000/svg"
                                            fill="none"
                                            viewBox="0 0 10 6"
                                        >
                                            <path
                                                stroke="currentColor"
                                                strokeLinecap="round"
                                                strokeLinejoin="round"
                                                strokeWidth="2"
                                                d="m1 1 4 4 4-4"
                                            />
                                        </svg>
                                    </button>
                                </li>
                            </ul>
                        </div>
                    </div>
                </nav>
            </nav>
            <MoreSideBar />
        </Fragment>
    );
}
