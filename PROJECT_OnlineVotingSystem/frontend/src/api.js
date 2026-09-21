import axios from "axios";
const api=axios.create({baseURL:"http://localhost:8080/api",timeout:8000});
api.interceptors.request.use(c=>{const t=localStorage.getItem("accessToken");if(t)c.headers.Authorization=`Bearer ${t}`;return c});
export default api;
