import React, { useEffect, useState } from "react";
import StarIcon from "@mui/icons-material/Star";
import ApiIcon from "@mui/icons-material/Api";
import FavoriteIcon from "@mui/icons-material/Favorite";
import ArrowCircleRightIcon from "@mui/icons-material/ArrowCircleRight";
import { Link, useNavigate } from "react-router-dom";
import { useDispatch, useSelector } from "react-redux";

function SearchContent() {
    const dispatch = useDispatch();
    const [productData, setProductData] = useState([]);
    const [searchCount, setSearchCount] = useState(0);
    const [showSort, setShowSort] = useState(false);
    const storeInfo = useSelector((state) => state.adminStore.storeInfo || {});
    const categories = useSelector((state) => state.adminStore.categories || []);
    const searchProducts = useSelector((state) => state.adminStore.searchProducts || []);
    const searchParamsResult = useSelector((state) => state.adminStore.searchParamsResult || "");
    const navigate = useNavigate();

    useEffect(() => {
        setProductData(searchProducts);
        setSearchCount(searchProducts.length);
    }, [searchProducts]);

    const handleSort = (type) => {
        let sortedData = [...productData];

        switch (type) {
            case "alphabet-asc":
                sortedData.sort((a, b) => a.title.localeCompare(b.title));
                break;
            case "alphabet-desc":
                sortedData.sort((a, b) => b.title.localeCompare(a.title));
                break;
            case "id-asc":
                sortedData.sort((a, b) => a.id - b.id);
                break;
            case "id-desc":
                sortedData.sort((a, b) => b.id - a.id);
                break;
            default:
                break;
        }

        setProductData(sortedData);
    };

    return (
        <div className="font-shopFont">
            <div className="w-full h-full grid grid-cols-3 pt-4 text-base font-light text-gray-600">
                <div className="pl-4">
                    Top {searchCount} results for "{searchParamsResult}" in "all {storeInfo.name}"
                </div>
                <div></div>
                <div
                    className="text-right pr-2"
                    onMouseLeave={() => {
                        setShowSort(false);
                    }}
                >
                    <button
                        onClick={() => {
                            setShowSort(!showSort);
                        }}
                        id="dropdownDefaultButton"
                        className="text-black bg-white outline-black hover:bg-gray-300 hover:ring-1 hover:outline-none hover:ring-black font-light rounded-lg text-sm px-5 py-2.5 text-center inline-flex items-center"
                        type="button"
                    >
                        Sort by{" "}
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
                    {showSort && (
                        <div
                            id="dropdown"
                            className="right-[8px] z-10 absolute bg-white divide-y divide-gray-100 rounded-lg shadow w-44 dark:bg-gray-700"
                        >
                            <ul
                                className="py-2 text-left text-sm text-gray-700 dark:text-gray-200"
                                aria-labelledby="dropdownDefaultButton"
                            >
                                <li>
                                    <button
                                        onClick={() => handleSort("alphabet-asc")}
                                        className="block w-full text-left px-4 py-2 hover:bg-gray-100 dark:hover:bg-gray-600 dark:hover:text-white"
                                    >
                                        Alphabet: A - Z
                                    </button>
                                </li>
                                <li>
                                    <button
                                        onClick={() => handleSort("alphabet-desc")}
                                        className="block w-full text-left px-4 py-2 hover:bg-gray-100 dark:hover:bg-gray-600 dark:hover:text-white"
                                    >
                                        Alphabet: Z - A
                                    </button>
                                </li>
                                <li>
                                    <button
                                        onClick={() => handleSort("id-asc")}
                                        className="block w-full text-left px-4 py-2 hover:bg-gray-100 dark:hover:bg-gray-600 dark:hover:text-white"
                                    >
                                        Date: Earlier to New
                                    </button>
                                </li>
                                <li>
                                    <button
                                        onClick={() => handleSort("id-desc")}
                                        className="block w-full text-left px-4 py-2 hover:bg-gray-100 dark:hover:bg-gray-600 dark:hover:text-white"
                                    >
                                        Date: New to Earlier
                                    </button>
                                </li>
                            </ul>
                        </div>
                    )}
                </div>
            </div>

            <div className="max-w-screen-2xl mx-auto grid grid-cols-1 md:grid-cols-2 xl:grid-cols-4 gap-6 xl:gap-10 px-4 pt-10 z-0 pb-4">
                {productData.map((product) => (
                    <div
                        key={product.id}
                        className="bg-white h-auto border-[1px] border-gray-200 py-8 hover:border-transparent shadow-none hover:shadow-testShadow duration-200 flex flex-col gap-4 relative"
                    >
                        <div className="w-full h-auto flex items-center justify-center relative group">
                            <img
                                className="w-52 h-64 object-contain"
                                src={product.mainPicture}
                                alt="ProductImg"
                            ></img>
                            <ul className="w-full h-36 bg-gray-100 absolute bottom-[-170px] flex flex-col items-end justify-center gap-2 font-titleFont px-2 border-1 border-r group-hover:bottom-0 duration-700">
                                <li className="productLi">
                                    Compare
                                    <span>
                    <ApiIcon />
                  </span>
                                </li>
                                <Link to={`/product/${product.id}`} className="productLi">
                                    View Details
                                    <span>
                    <ArrowCircleRightIcon />
                  </span>
                                </Link>
                                <li className="productLi">
                                    Add to Wish List
                                    <span>
                    <FavoriteIcon />
                  </span>
                                </li>
                            </ul>
                        </div>
                        <div className="px-4 z-0 bg-white">
                            <div className="flex items-center justify-between">
                                <h2 className="font-titleFont tracking-wide text-lg text-amazon_blue font-medium">
                                    {product.title.substring(0, 40)}...
                                </h2>
                            </div>
                            <div>
                                <div>
                                    <p className="text-sm">{product.description.substring(0, 100)}...</p>
                                </div>
                                <div className="text-yellow-500">
                                    <StarIcon />
                                    <StarIcon />
                                    <StarIcon />
                                    <StarIcon />
                                    <StarIcon />
                                </div>
                            </div>
                            <button
                                onClick={() => navigate(`/product/${product.id}`)}
                                className="w-full font-titleFont font-medium text-base bg-gradient-to-tr from-yellow-400 to-yellow-200 border hover:from-yellow-300 hover:to-yellow-400 border-yellow-500 hover:border-yellow-700 active:bg-gradient-to-bl active:from-yellow-400 active:to-yellow-500 duration-200 py-1.5 rounded-md mt-3"
                            >
                                View Details
                            </button>
                        </div>
                    </div>
                ))}
            </div>
        </div>
    );
}

export default SearchContent;
