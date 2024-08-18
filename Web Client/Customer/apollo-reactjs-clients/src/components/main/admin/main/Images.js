import React from 'react'
import { useState, useEffect } from "react";
import {
  ref,
  uploadBytes,
  getDownloadURL,
  uploadBytesResumable,
  listAll
} from "firebase/storage";
import Swal from 'sweetalert2';
import { firebaseStorage } from "../../../../firebase";
import { v4 } from "uuid";
import AddAPhotoIcon from '@mui/icons-material/AddAPhoto';
import { useDispatch, useSelector } from 'react-redux';
import { createImagesList } from '../../../../features/variant/ImageSlide';
import { deleteVariant } from '../../../../features/variant/variantSlice'
import { Link } from 'react-router-dom';



function Images() {
  const [imageUpload, setImageUpload] = useState(null);
  const [imageUrls, setImageUrls] = useState([]);
  const variantList = useSelector((state) => state.variant.variants);
  const [selectedVariantId, setSelectedVariantId] = useState(null);
  const [showForm, setShowForm] = useState(false);
  const dispatch = useDispatch();
  const [variantListCreated, setVariantListCreated] = useState(variantList);
  
  const handleMultipleFileUpload = (files) => {

    if (files.length > 0) {
      const uploadedFiles = [];
      console.log(files);

      // Iterate through the selected files
      for (const file of files) {
        const storageRef = ref(firebaseStorage, `files/${file.name} + ${v4()}`);
        const uploadTask = uploadBytesResumable(storageRef, file);

        uploadTask.on(
          "state_changed",
          (snapshot) => {
            const progress = Math.round((snapshot.bytesTransferred / snapshot.totalBytes) * 100);
            // Update progress for each file, if needed
          },
          (error) => {
            // Handle errors for each file, if needed
          },
          () => {
            getDownloadURL(uploadTask.snapshot.ref).then((downloadURL) => {
              // Save the download URL for each file, if needed
              uploadedFiles.push(downloadURL);

              // Check if all files have been uploaded
              if (uploadedFiles.length === files.length) {
                // All files have been uploaded, do something with the uploaded file URLs
                console.log(uploadedFiles);
                setImageUrls(uploadedFiles);
              }
            });
            
          }
        );
      }
    }
    console.log(imageUrls);

  }
  const openDeleteVariantModal = (variantId) => {
    dispatch(deleteVariant({ variantId: variantId }));
    Swal.fire({
      title: variantId + 'is deleted !!! ',
      showClass: {
        popup: 'animate__animated animate__fadeInDown'
      },
      hideClass: {
        popup: 'animate__animated animate__fadeOutUp'
      }
    })
  }
  const handleClickToUpdate = (id) => {
    setSelectedVariantId(id);
    setShowForm(true);
  }
  const addImageForVariant = () => {
    dispatch(createImagesList({ imageUrls: imageUrls, variantId: selectedVariantId }));
    setShowForm(false);
    Swal.fire({
      title: 'Add images successfull ! ',
      showClass: {
        popup: 'animate__animated animate__fadeInDown'
      },
      hideClass: {
        popup: 'animate__animated animate__fadeOutUp'
      }
    })

  }
  
  return (
    <div className='p-0'>
      {showForm && 
      <div>
          <h1 className='text-6xl text-center'><AddAPhotoIcon /></h1>
          <div className="flex items-center justify-center w-54 h-54 px-9">
            <label htmlFor="dropzone-file" className="flex flex-col items-center justify-center w-full h-64 border-2 border-gray-300 border-dashed rounded-lg cursor-pointer bg-gray-50 dark:hover:bg-bray-800 dark:bg-gray-700 hover:bg-gray-100 dark:border-gray-600 dark:hover:border-gray-500 dark:hover:bg-gray-600">
              <div className="flex flex-col items-center justify-center pt-5 pb-6">
                <svg className="w-8 h-8 mb-4 text-gray-500 dark:text-gray-400" aria-hidden="true" xmlns="http://www.w3.org/2000/svg" fill="none" viewBox="0 0 20 16">
                  <path stroke="currentColor" strokeLinecap="round" strokeLinejoin="round" strokeWidth="2" d="M13 13h3a3 3 0 0 0 0-6h-.025A5.56 5.56 0 0 0 16 6.5 5.5 5.5 0 0 0 5.207 5.021C5.137 5.017 5.071 5 5 5a4 4 0 0 0 0 8h2.167M10 15V6m0 0L8 8m2-2 2 2" />
                </svg>
                <p className="mb-2 text-sm text-gray-500 dark:text-gray-400"><span className="font-semibold">Click to upload</span> or drag and drop</p>
                <p className="text-xs text-gray-500 dark:text-gray-400">SVG, PNG, JPG or GIF (MAX. 800x400px)</p>
              </div>
              <input id="dropzone-file" type="file"
                onChange={(event) => {
                  handleMultipleFileUpload(event.target.files);
                }} className="hidden" multiple />
            </label>
          </div>
          <div className='flex items-center justify-center'>
            {imageUrls.length > 0 && imageUrls.map((item, index) => (
              <div className=''>
                <img key={index} alt='hihi' src={item} className="rounded-xl w-20 h-15 mt-5 mr-10" />

              </div>
            ))}
          </div>
          <button className="h-full w-25 text-center bg-slate-700 " onClick={addImageForVariant}>Add images for variantID {selectedVariantId} </button>
      </div>}
      <Link to="/dashboard"><div onClick={() => { setVariantListCreated ([])}}>NEXT</div></Link>
      {variantListCreated.length > 0 ?
        <div className="relative overflow-x-auto shadow-md sm:rounded-lg">
          <table className="w-full text-sm text-left text-gray-500 dark:text-gray-400">
            <thead className="text-xs text-gray-700 uppercase bg-gray-50 dark:bg-gray-700 dark:text-gray-400">
              <tr>
                <th scope="col" className="px-6 py-3">
                  VARIANT
                </th>
                <th scope="col" className="px-6 py-3">
                  TITLE
                </th>
                <th scope="col" className="px-6 py-3">
                  SKU
                </th>
                <th scope="col" className="px-6 py-3">
                  STOCK
                </th>
                <th scope="col" className="px-6 py-3">
                  WEIGHT
                </th>
                <th scope="col" className="px-6 py-3">
                  PRICE
                </th>
                <th scope="col" className="px-6 py-3">
                  SALE PRICE
                </th>
                <th scope="col" className="px-6 py-3">
                </th>
              </tr>
            </thead>
            <tbody>
              {variantListCreated && variantListCreated.map((variant, index) => (
                <tr key={index} className="bg-white border-b dark:bg-gray-900 dark:border-gray-700">
                  <td scope="row" className="px-6 py-4 font-medium text-gray-900 whitespace-nowrap dark:text-white">
                    {variant.optionValueDTOList.map(item => item.value).join('/')}
                  </td>
                  <td className="px-6 py-4">
                    <span>{variant.name}</span>
                  </td>
                  <td className="px-6 py-4">
                    <span>{variant.skuCode}</span>
                  </td>
                  <td className="px-6 py-4">
                    <span>{variant.stockQuantity}</span>
                  </td>
                  <td className="px-6 py-4">
                    <span>{variant.weight}</span>
                  </td>
                  <td className="px-6 py-4">
                    <span>{variant.price}</span>
                  </td>
                  <td className="px-6 py-4">
                    <span>{variant.salePrice}</span>
                  </td>
                  <td className="px-3 py-4 flex">
                    <button onClick={() => handleClickToUpdate(variant.id)}  className="mr-4 block text-white bg-blue-700 hover:bg-blue-800 focus:ring-4 focus:outline-none focus:ring-blue-300 font-medium rounded-lg text-sm px-5 py-2.5 text-center dark:bg-blue-600 dark:hover:bg-blue-700 dark:focus:ring-blue-800"><AddAPhotoIcon/></button>
                    <button onClick={() => { openDeleteVariantModal(variant.id) }} className="block text-white bg-blue-700 hover:bg-blue-800 focus:ring-4 focus:outline-none focus:ring-blue-300 font-medium rounded-lg text-sm px-5 py-2.5 text-center dark:bg-blue-600 dark:hover:bg-blue-700 dark:focus:ring-blue-800">Delete</button>
                  </td>
                </tr>
              ))}
            </tbody>
          </table>
        </div> : <></>}
    </div>
  )
}

export default Images
