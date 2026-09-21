import {useState} from "react";
import {useNavigate} from "react-router-dom";
import {ArrowRight,CheckCircle2,Eye,EyeOff,ShieldCheck} from "lucide-react";
import api from "../api";

export default function Register(){
 const nav=useNavigate();
 const [step,setStep]=useState(1);
 const [form,setForm]=useState({name:"",dob:"",gender:"",mobile:"",email:"",identityRef:"",voterId:"",password:"",confirm:""});
 const [otp,setOtp]=useState("");
 const [show,setShow]=useState(false);
 const [loading,setLoading]=useState(false);
 const [err,setErr]=useState("");
 const [created,setCreated]=useState("");
 const update=(k,v)=>setForm({...form,[k]:v});
 const nextBasic=e=>{e.preventDefault();setErr("");setStep(2)};
 const verifyIdentity=()=>{setErr("");setStep(3)};
 const createAccount=async e=>{
   e.preventDefault(); setErr("");
   if(form.password.length<8) return setErr("Password must contain at least 8 characters.");
   if(form.password!==form.confirm) return setErr("Passwords do not match.");
   if(!otp.trim()) return setErr("Enter the demo OTP 123456.");
   setLoading(true);
   try{
     const r=await api.post("/auth/register",{voterId:form.voterId.trim(),fullName:form.name,dob:form.dob,gender:form.gender,email:form.email,phone:form.mobile,identityRef:form.identityRef,password:form.password,otp});
     setCreated(r.data.voterId);setStep(4);
   }catch(x){setErr(x.response?.data?.message||"Registration failed. Please try again.");}
   finally{setLoading(false)}
 };
 return <main className="auth-page">
  <div className="auth-art"><div className="art-logo"><ShieldCheck/> VoteSecure</div><div><div className="eyebrow">NEW VOTER REGISTRATION</div><h1>Create your<br/><em>secure identity.</em></h1><p>Create your own voter credentials and use them to sign in from any browser session.</p></div><div className="art-note"><CheckCircle2/> Demo verification · no real Aadhaar/UIDAI connection</div></div>
  <div className="auth-card">
   <div className="stepper"><span className={step>=1?"active":""}>01</span><i/><span className={step>=2?"active":""}>02</span><i/><span className={step>=3?"active":""}>03</span></div>
   {step===1&&<><div className="eyebrow">BASIC INFORMATION</div><h2>Create your voter account</h2><p className="muted">These details will be stored in the VoteSecure database.</p><form onSubmit={nextBasic}><label>Full name<input required value={form.name} onChange={e=>update("name",e.target.value)} placeholder="Your full name"/></label><div className="two-col"><label>Date of birth<input type="date" required value={form.dob} onChange={e=>update("dob",e.target.value)}/></label><label>Mobile number<input required pattern="[0-9]{10}" value={form.mobile} onChange={e=>update("mobile",e.target.value)} placeholder="10-digit mobile"/></label></div><label>Gender<select required value={form.gender} onChange={e=>update("gender",e.target.value)}><option value="">Select gender</option><option>Female</option><option>Male</option><option>Other</option></select></label><label>Email address<input type="email" required value={form.email} onChange={e=>update("email",e.target.value)} placeholder="you@example.com"/></label><button className="primary-btn full">Continue <ArrowRight/></button></form></>}
   {step===2&&<><div className="eyebrow">IDENTITY VERIFICATION</div><h2>Verify your identity</h2><p className="muted">Academic demonstration only. Enter any test identification number.</p><label>Demo identification number<input required value={form.identityRef} onChange={e=>update("identityRef",e.target.value)} placeholder="XXXX XXXX XXXX"/></label><div className="demo-box">🔒 For the demo, OTP verification uses <b>123456</b>.</div><button className="primary-btn full" onClick={verifyIdentity}>Send OTP <ArrowRight/></button></>}
   {step===3&&<><div className="eyebrow">CREATE CREDENTIALS</div><h2>Set your login details</h2><p className="muted">Use these exact credentials later on the Login page.</p><form onSubmit={createAccount}><label>Voter ID / Username<input required minLength="4" value={form.voterId} onChange={e=>update("voterId",e.target.value)} placeholder="e.g. JANU001"/></label><label>Password<div className="password-wrap"><input required minLength="8" type={show?"text":"password"} value={form.password} onChange={e=>update("password",e.target.value)} placeholder="Minimum 8 characters"/><button type="button" onClick={()=>setShow(!show)}>{show?<EyeOff/>:<Eye/>}</button></div></label><label>Confirm password<input required type="password" value={form.confirm} onChange={e=>update("confirm",e.target.value)} placeholder="Re-enter password"/></label><label>6-digit OTP<input required inputMode="numeric" maxLength="6" value={otp} onChange={e=>setOtp(e.target.value.replace(/\D/g,"").slice(0,6))} placeholder="Demo OTP: 123456"/></label>{err&&<div className="alert error">⚠ {err}</div>}<button className="primary-btn full" disabled={loading}>{loading?"Creating account…":"CREATE ACCOUNT"}</button></form></>}
   {step===4&&<div className="success-state"><div className="success-icon"><CheckCircle2/></div><div className="eyebrow">REGISTRATION COMPLETE</div><h2>Your account is ready.</h2><p className="muted">Your credentials have been saved in the database.</p><div className="demo-box"><b>Voter ID</b><span>{created}</span><b>Status</b><span>✓ VERIFIED</span></div><button className="primary-btn full" onClick={()=>nav("/login",{state:{registeredId:created}})}>Continue to Login <ArrowRight/></button></div>}
  </div>
 </main>
}
