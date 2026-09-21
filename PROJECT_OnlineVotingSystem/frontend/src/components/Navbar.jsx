import {Link,useNavigate} from "react-router-dom"; import {Bell,Menu,ShieldCheck,LogOut,X} from "lucide-react"; import {useState} from "react";
export default function Navbar(){
 const nav=useNavigate(),[open,setOpen]=useState(false),logged=!!localStorage.getItem("accessToken");
 const logout=()=>{localStorage.clear();nav("/login")};
 return <header className="topbar"><div className="nav-inner">
  <Link to="/" className="brand"><span className="brand-mark"><ShieldCheck size={21}/></span><span>Vote<span>Secure</span></span></Link>
  <nav className={open?"navlinks mobile-open":"navlinks"}><Link to="/">Home</Link><Link to="/election">Election</Link><Link to="/candidates">Candidates</Link><Link to="/results">Results</Link><Link to="/help">Help</Link></nav>
  <div className="nav-actions">{logged&&<button className="icon-btn" title="Notifications"><Bell size={19}/></button>}{logged?<><button className="profile-chip" onClick={()=>nav("/voter/profile")}>JD <span>Voter</span></button><button className="outline-btn" onClick={logout}><LogOut size={16}/> Logout</button></>:<button className="primary-btn" onClick={()=>nav("/login")}>Login</button>}<button className="menu-btn" onClick={()=>setOpen(!open)}>{open?<X/>:<Menu/>}</button></div>
 </div></header>
}
