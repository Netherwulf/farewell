export const getRequestConfig = (authToken) => {
    return {
        headers: {
            'Authorization': authToken,
        }
    }
}