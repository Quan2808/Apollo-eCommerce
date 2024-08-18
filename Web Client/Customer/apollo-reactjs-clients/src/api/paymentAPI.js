import axios from "axios";

const PAYMENT_METHOD_API = "http://localhost:9999/api/payments";

export const findPaymentMethod = async (userId) => {
    let result = null;
    try {
        result = await axios.get(`${PAYMENT_METHOD_API}/payment-method/${userId}`);
    } catch (e) {
        console.log("Find payment method API error:" + e);
    }
    return result;
}

export const addPaymentMethod = async (paymentMethod) => {
    let result = null;
    try {
        result = await axios.post(`${PAYMENT_METHOD_API}/payment-method`,paymentMethod);
    } catch (e) {
        console.log("Add payment method API error: " + e);
    }
    return result;
}

export const findShippingMethod = async () => {
    let result = null;
    try {
        result = await axios.get(`${PAYMENT_METHOD_API}/shipping-method`)
    } catch (e) {
        console.log("Find shipping method API error: " + e);
    }
    return result;
}

export const findAddress = async (userId) => {
    let result = null;
    try {
        result = await axios.get(`${PAYMENT_METHOD_API}/address/${userId}`)
    } catch (e) {
        console.log("Find address API error: " + e);
    }
    return result;
}

export const addAddress = async (address) => {
    let result = null;
    try {
        result = await axios.post(`${PAYMENT_METHOD_API}/address`, address);
    } catch (e) {
        console.log("Add address method API error: " + e);
    }
    return result;
}

export const updateAddress = async (address) => {
    let result = null;
    try {
        result = await axios.put(`${PAYMENT_METHOD_API}/address`, address);
    } catch (e) {
        console.log("update address API error: " + e);
    }
    return result;
};

export const findShopOrder = async (userId) => {
    let result = null;
    try {
        result = await axios.get(`${PAYMENT_METHOD_API}/${userId}}`)
    } catch (e) {
        console.log("add shop order API error: " + e);
    }
    return result;
}

export const createShopOrder = async (shopOrder) => {
    let result = null;
    try {
        result = await axios.post(`${PAYMENT_METHOD_API}`, shopOrder);
    } catch (e) {
        console.log()
    }
    return result;
}