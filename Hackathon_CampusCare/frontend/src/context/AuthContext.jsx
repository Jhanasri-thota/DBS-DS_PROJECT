import React,{createContext,useContext,useMemo,useState}from'react';
const C=createContext(null);
export function AuthProvider({children}){const[user,setUser]=useState(()=>{try{return JSON.parse(localStorage.getItem('campuscare_user'))}catch{return null}});function login(u,t){localStorage.setItem('campuscare_user',JSON.stringify(u));localStorage.setItem('campuscare_token',t);setUser(u)}function logout(){localStorage.removeItem('campuscare_user');localStorage.removeItem('campuscare_token');setUser(null)}const value=useMemo(()=>({user,login,logout}),[user]);return <C.Provider value={value}>{children}</C.Provider>}
export function useAuth(){return useContext(C)}
