import React, { Fragment } from "react";
import {
  Outlet,
  Route,
  RouterProvider,
  ScrollRestoration,
  createBrowserRouter,
  createRoutesFromElements,
} from "react-router-dom";
import Home from "./pages/Home";
import Cart from "./components/main/cart/Cart";
import Signin from "./pages/Signin";
import HomeContent from "./components/main/store/HomeContent";
import DealsContent from "./components/main/store/DealsContent";
import CategoryContent from "./components/main/store/CategoryContent";
import SearchContent from "./components/main/store/SearchContent";
import Error from "./pages/Error";
import Registration from "./pages/Registration";
import Header from "./components/common/header/Header";
import Footer from "./components/common/footer/Footer";
import ProductDetail from "./components/main/variant/ProductDetail";
import AdminHeader from "./components/common/adminheader/AdminHeader";
import Admin from "./components/main/admin/Admin";
import ShopCreat from "./components/main/admin/main/ShopCreate";
import Category from "./components/main/admin/main/Category";
import Inventory from "./components/main/admin/Inventory";
import Orders from "./components/main/admin/Orders";
import Info from "./components/main/admin/Info";
import Payment from "./pages/Payment";
import StoreHeader from "./components/main/store/StoreHeader";
import StoreFooter from "./components/main/store/StoreFooter";
import StoreBanner from "./components/main/store/StoreBanner";
import Variants from "./components/main/admin/main/Variants";
import Product from "./components/main/admin/main/Product";
import Offers from "./components/main/admin/main/Offers";
import Dashboard from "./components/main/dashboard/Dashboard";
import DashBoardHeader from "./components/main/dashboard/DashBoardHeader";
import Categories from "./components/main/dashboard/element/Categories";
import HomeDasboard from "./components/main/dashboard/element/HomeDasboard";
import OrdersDashboard from "./components/main/dashboard/element/OrdersDashboard";
import Products from "./components/main/dashboard/element/Products";
import Profile from "./components/main/dashboard/element/Profile";
import Images from "./components/main/admin/main/Images";
import HomeAdmin from "./components/main/admin/main/HomeAdmin";
import AdminRegistration from "./pages/AdminRegistration";
import AdminSignin from "./pages/AdminSignin";
import SubCategoryContent from "./components/main/store/SubCategoryContent";
import Confirm from "./pages/Confirm";

const Layout = () => {
  return (
    <div>
      <Header />
      <ScrollRestoration />
      <Outlet />
      <Footer />
    </div>
  );
};
const AdminLayout = () => {
  return (
    <div>
      <AdminHeader />
      <Admin>
        <Outlet />
      </Admin>
      <Footer />
    </div>
  );
};
const DashboardLayout = () => {
  return (
    <div>
      <DashBoardHeader />
      <Dashboard>
        <Outlet />
      </Dashboard>
    </div>
  );
};

const StoreLayout = () => {
  return (
    <div>
      <StoreBanner />
      <StoreHeader />
      <Outlet />
      <StoreFooter />
    </div>
  );
};

function App() {
  const router = createBrowserRouter(
    createRoutesFromElements(
      <Fragment>
        <Route path="/confirm" element={<Confirm />} />
        <Route path="/error" element={<Error />} />
        <Route path="/register" element={<Registration />} />
        <Route path="/signin" element={<Signin />} />
        <Route
          path="/admincentral/register"
          element={<AdminRegistration />}
        />
        <Route path="/admincentral/signin" element={<AdminSignin />} />
        <Route path="/" element={<Layout />}>
          <Route index element={<Home />} />
          <Route path="/cart" element={<Cart />} />
          <Route path="/product/:id" element={<ProductDetail />} />
          <Route path="/store/:id" element={<StoreLayout />}>
            <Route index element={<HomeContent />} />
            <Route path="/store/:id/deals" element={<DealsContent />}></Route>
            <Route path="/store/:id/search" element={<SearchContent />}></Route>
            <Route
              path="/store/:id/:categoryName"
              element={<CategoryContent />}
            ></Route>
            <Route
              path="/store/:id/:categoryName/:subCategoryName"
              element={<SubCategoryContent />}
            ></Route>
          </Route>
        </Route>
        <Route path="/payment" element={<Payment />} />
        <Route path="/admin" element={<AdminLayout />}>
          <Route index element={<HomeAdmin />} />
          <Route path="/admin/shop" element={<ShopCreat />} />
          <Route path="/admin/category" element={<Category />} />
          <Route path="/admin/product" element={<Product />} />
          <Route path="/admin/attributes" element={<Offers />} />
          <Route path="/admin/variant" element={<Variants />} />
          <Route path="/admin/images" element={<Images />} />
        </Route>
        <Route path="/dashboard" element={<DashboardLayout />}>
          <Route index element={<HomeDasboard />} />
          <Route path="/dashboard/categories" element={<Categories />} />
          <Route path="/dashboard/products" element={<Products />} />
          <Route path="/dashboard/orders" element={<OrdersDashboard />} />
          <Route path="/dashboard/profile" element={<Profile />} />
        </Route>
      </Fragment>
    )
  );
  return (
    <div className="font-bodyFont bg-gray-100">
      <RouterProvider router={router}></RouterProvider>
    </div>
  );
}

export default App;
