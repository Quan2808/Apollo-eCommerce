import React, { useState } from 'react';
import { useForm } from 'react-hook-form';
import { firebaseStorage } from '../../../../firebase';
import { getDownloadURL, ref, uploadBytesResumable } from 'firebase/storage';
import { v4 } from "uuid";
import { useNavigate } from 'react-router-dom';
import { useDispatch, useSelector } from 'react-redux';
import { addProduct } from '../../../../features/variant/productSlide';
import { selectStoreCategoryList } from '../../../../features/variant/storeCategorySlide';

function Product() {
    const adminInfo = useSelector((state) => state.admin.adminInfo);
    const category = useSelector((state) => state.category.category);
    const store = useSelector((state) => state.shop.store);
    const storecategories = useSelector(selectStoreCategoryList);

    const [firebaseFile, setFirebaseFile] = useState('');
    const [progresspercent, setProgresspercent] = useState(0);
    const { register, handleSubmit, formState: { errors }, reset } = useForm();
    const dispatch = useDispatch();
    const navigate = useNavigate();
    const [selectedYourCate, setSelectedYourCate] = useState(null);

    const registerOptions = {
        title: { required: "Title is required" },
        description: {
            required: "Description is required",
            minLength: { value: 20, message: "Description must be at least 20 characters" }
        },
        file: { required: "File is required" }
    };

    const handleFile = (e) => {
        const file = e.target.files[0];
        if (file) {
            const storageRef = ref(firebaseStorage, `files/${file.name} + ${v4()}`);
            const uploadTask = uploadBytesResumable(storageRef, file);

            uploadTask.on("state_changed",
                (snapshot) => {
                    const progress = Math.round((snapshot.bytesTransferred / snapshot.totalBytes) * 100);
                    setProgresspercent(progress);
                },
                (error) => {
                    console.error("Error uploading file:", error);
                    alert("Failed to upload file. Please try again.");
                },
                () => {
                    getDownloadURL(uploadTask.snapshot.ref).then((downloadURL) => {
                        setFirebaseFile(downloadURL);
                    });
                }
            );
        }
    };

    const onSubmit = async (data) => {
        const newProduct = {
            title: data.title,
            description: data.description,
            mainPicture: firebaseFile,
        };

        dispatch(addProduct({
            product: newProduct,
            storeId: store?.id,
            categoryId: category?.id,
            storeCategoryId: selectedYourCate ? selectedYourCate.id : null
        }));

        // Reset form after submission
        reset();
        setFirebaseFile('');
        setProgresspercent(0);

        navigate("/admin/variant");
    };

    const handleSelectChange = (event) => {
        const selectedId = parseInt(event.target.value, 10);
        const selectedObject = storecategories.find(option => option.id === selectedId);
        setSelectedYourCate(selectedObject);
    };

    return (
        <div className='col-span-8 border-2 border-gray-500 p-4 text-titleFont ml-20 mr-20 rounded-xl'>
            <span className='text-sm text-titleFont text-left'> All Department &gt;&gt;&gt; {category?.attribute}</span>
            <form className='items-center' onSubmit={handleSubmit(onSubmit)}>
                <div className="grid grid-cols-2">
                    <div className='col-span-1 mr-4'>
                        <div className='text-titleFont mt-5'>
                            <label htmlFor="title" className='mb-2 text-sm font-medium text-gray-900 dark:text-gray mt-5'>Title</label>
                            <br></br>
                            <input
                                className="bg-gray-50 border border-gray-300 text-gray-900 text-sm rounded-lg focus:ring-blue-500 focus:border-blue-500 block w-full p-2.5 dark:bg-gray-700 dark:border-gray-600 dark:placeholder-gray-400 dark:text-white dark:focus:ring-blue-500 dark:focus:border-blue-500"
                                name="title"
                                type="text" {...register("title", registerOptions.title)} />
                            <small className='text-red-700 text-titleFont text-sm'>
                                {errors?.title && errors.title.message}
                            </small>
                        </div>
                        <div className='text-titleFont mt-5'>
                            <label htmlFor="description" className='mb-2 text-sm font-medium text-gray-900 dark:text-gray mt-5'>Description</label>
                            <br></br>
                            <br></br>
                            <input
                                className="bg-gray-50 border border-gray-300 text-gray-900 text-sm rounded-lg focus:ring-blue-500 focus:border-blue-500 block w-full p-2.5 dark:bg-gray-700 dark:border-gray-600 dark:placeholder-gray-400 dark:text-white dark:focus:ring-blue-500 dark:focus:border-blue-500"
                                type="text"
                                name="description"
                                {...register("description", registerOptions.description)}
                            />
                            <small className='text-red-700 text-titleFont'>
                                {errors?.description && errors.description.message}
                            </small>
                        </div>
                        <div className='text-titleFont mt-5'>
                            <label htmlFor="file" className='mb-2 text-sm font-medium text-gray-900 dark:text-gray mt-5'>Image</label>
                            <br></br>
                            <input
                                className="bg-gray-50 border border-gray-300 text-gray-900 text-sm rounded-lg focus:ring-blue-500 focus:border-blue-500 block w-full p-2.5 dark:bg-gray-700 dark:border-gray-600 dark:placeholder-gray-400 dark:text-white dark:focus:ring-blue-500 dark:focus:border-blue-500 "
                                type="file" name="file"
                                {...register("file", registerOptions.file)}
                                onChange={handleFile}
                            />
                            <small className='text-red-700 text-titleFont'>
                                {errors?.file && errors.file.message}
                            </small>
                            {firebaseFile && <img alt='Uploaded file' src={firebaseFile} className="rounded-3xl w-30 h-30 mt-10" />}
                            {
                                !firebaseFile &&
                                <div className='outerbar'>
                                    <div className='innerbar text-titleFont'>{progresspercent}%</div>
                                </div>
                            }
                        </div>
                    </div>
                    <div className='col-span-1 '>
                        {storecategories && (
                            <div>
                                <label htmlFor="category" className="block mb-2 text-sm font-medium text-gray-900 dark:text-gray mt-4">Select The Available Category</label>
                                <select onChange={handleSelectChange} className="bg-gray-50 border border-gray-300 text-gray-900 text-sm rounded-lg focus:ring-blue-500 focus:border-blue-500 block w-full p-2.5 dark:bg-gray-700 dark:border-gray-600 dark:placeholder-gray-400 dark:text-white dark:focus:ring-blue-500 dark:focus:border-blue-500">
                                    <option value="">Select an option</option>
                                    {storecategories.map(option => (
                                        <option key={option.id} value={option.id}>
                                            {option.name}
                                        </option>
                                    ))}
                                </select>
                                <div>
                                    Selected ID: {selectedYourCate ? `${selectedYourCate.id} ${selectedYourCate.name}` : 'None selected'}
                                </div>
                            </div>
                        )}
                        <div className='text-titleFont mt-5'>
                            <label className='mb-2 text-sm font-medium text-gray-900 dark:text-gray mt-5'>Shop Id</label>
                            <br></br>
                            <div
                                className="bg-gray-50 border border-gray-300 text-gray-900 text-sm rounded-lg focus:ring-blue-500 focus:border-blue-500 block w-full p-2.5 dark:bg-gray-700 dark:border-gray-600 dark:placeholder-gray-400 dark:text-white dark:focus:ring-blue-500 dark:focus:border-blue-500"
                            >{store?.id}</div>
                        </div>
                    </div>
                </div>
                <div className="text-center">
                    <button type="submit" className='w-30 h-30 bg-amazon_blue text-xl text-white px-10 rounded-2xl mt-5'>Next</button>
                </div>
            </form>
        </div>
    );
}

export default Product;
