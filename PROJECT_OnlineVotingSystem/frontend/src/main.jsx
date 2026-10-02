import React, { useEffect, useMemo, useState } from 'react';
import { createRoot } from 'react-dom/client';
import axios from 'axios';
import './style.css';

const API = 'http://localhost:8080/api';
const api = axios.create({ baseURL: API });
api.interceptors.request.use(config => {
  const token = localStorage.getItem('token');
  if (token) config.headers.Authorization = `Bearer ${token}`;
  return config;
});

const initials = name => (name || 'V').split(' ').map(x => x[0]).slice(0, 2).join('').toUpperCase();
const prettyStatus = s => (s || '').replaceAll('_', ' ');
const formatDate = d => d ? new Date(d).toLocaleString('en-IN', { dateStyle: 'medium', timeStyle: 'short' }) : '—';
const errorText = e => e?.response?.data?.error || e?.message || 'Something went wrong';

function App() {
  const [page, setPage] = useState('home');
  const [user, setUser] = useState(() => { try { return JSON.parse(localStorage.getItem('user') || 'null'); } catch { return null; } });
  const [pending, setPending] = useState(null);
  const [toast, setToast] = useState(null);
  const [electionId, setElectionId] = useState(localStorage.getItem('electionId'));

  const go = p => { setPage(p); window.scrollTo({ top: 0, behavior: 'smooth' }); };
  const notify = (message, type = 'success') => { setToast({ message, type }); setTimeout(() => setToast(null), 3500); };
  const logout = async () => {
    localStorage.removeItem('token'); localStorage.removeItem('user'); localStorage.removeItem('electionId');
    setUser(null); setElectionId(null); go('home'); notify('You have been logged out.');
  };

  return <div className="app-shell">
    <Header user={user} page={page} go={go} logout={logout}/>
    <main className="main-wrap">
      {page === 'home' && <Home go={go}/>} 
      {page === 'voter-register' && <Register role="VOTER" go={go} setPending={setPending} notify={notify}/>} 
      {page === 'admin-register' && <Register role="ADMIN" go={go} setPending={setPending} notify={notify}/>} 
      {page === 'verify' && <Verify pending={pending} go={go} notify={notify}/>} 
      {page === 'login' && <Login setUser={setUser} go={go} notify={notify}/>} 
      {page === 'dashboard' && user && <Dashboard user={user} go={go} setElectionId={setElectionId}/>} 
      {page === 'elections' && <Elections user={user} go={go} setElectionId={setElectionId}/>} 
      {page === 'room' && <Room electionId={electionId} go={go} notify={notify}/>} 
      {page === 'history' && user && <History go={go}/>} 
      {page === 'profile' && user && <Profile user={user} go={go}/>} 
      {page === 'admin' && user?.role === 'ADMIN' && <Admin user={user} go={go} setElectionId={setElectionId}/>} 
      {!user && ['dashboard','history','profile','admin'].includes(page) && <Login setUser={setUser} go={go} notify={notify}/>} 
    </main>
    <Footer/>
    {toast && <div className={`toast ${toast.type}`}><span>{toast.type === 'success' ? '✓' : '!'}</span>{toast.message}</div>}
  </div>;
}

function Header({ user, page, go, logout }) {
  return <header className="topbar">
    <div className="topbar-inner">
      <button className="brand" onClick={() => go('home')}><span className="brand-mark">✓</span><span>Vote<span>Hub</span></span></button>
      <nav className="desktop-nav">
        <button className={page === 'home' ? 'active' : ''} onClick={() => go('home')}>Home</button>
        <button className={page === 'elections' || page === 'room' ? 'active' : ''} onClick={() => go('elections')}>Elections</button>
        <button onClick={() => document.getElementById('how')?.scrollIntoView({ behavior: 'smooth' })}>How it works</button>
      </nav>
      <div className="top-actions">
        {!user ? <><button className="ghost-btn" onClick={() => go('login')}>Sign in</button><button className="small-primary" onClick={() => go('voter-register')}>Get started</button></> : <>
          <button className="avatar-button" onClick={() => go(user.role === 'ADMIN' ? 'admin' : 'dashboard')}><span>{initials(user.name)}</span><b>{user.name}</b><i>⌄</i></button>
          <button className="icon-btn" onClick={logout} title="Logout">↪</button>
        </>}
      </div>
    </div>
  </header>;
}

function Home({ go }) {
  return <>
    <section className="hero-section">
      <div className="hero-copy">
        <div className="eyebrow"><span className="pulse-dot"/> SECURE DIGITAL VOTING</div>
        <h1>Your vote.<br/><span>Your voice.</span><br/>Your future.</h1>
        <p className="hero-sub">A modern college voting platform built for verified registration, one-vote enforcement, privacy-aware ballots and transparent election results.</p>
        <div className="hero-actions"><button className="cta" onClick={() => go('voter-register')}>Create voter account <span>→</span></button><button className="outline-btn" onClick={() => go('elections')}>Explore elections</button></div>
        <div className="trust-row"><div><b>01</b><span>Verified identity</span></div><div><b>02</b><span>One vote per election</span></div><div><b>03</b><span>Privacy-aware ballots</span></div></div>
      </div>
      <div className="hero-visual">
        <div className="orb orb-a"/><div className="orb orb-b"/>
        <div className="vote-card-main">
          <div className="card-top"><span className="live-badge"><i/> LIVE</span><span>2026</span></div>
          <div className="vote-symbol">✓</div>
          <h3>Student Council<br/>Election</h3>
          <p>One verified account. One secure ballot.</p>
          <div className="mini-progress"><span style={{width:'72%'}}/></div><div className="progress-meta"><span>Participation</span><b>72%</b></div>
          <button onClick={() => go('elections')}>Open election room <span>↗</span></button>
        </div>
        <div className="float-card verified-float"><span>✓</span><div><b>Identity verified</b><small>OTP protection active</small></div></div>
        <div className="float-card privacy-float"><span>◈</span><div><b>Privacy protected</b><small>Ballot separation enabled</small></div></div>
      </div>
    </section>

    <section className="feature-strip" id="how">
      <div><span className="feature-icon blue">◎</span><div><b>Verified registration</b><p>OTP-based account verification.</p></div></div>
      <div><span className="feature-icon green">✓</span><div><b>One-vote enforcement</b><p>Duplicate voting is blocked.</p></div></div>
      <div><span className="feature-icon purple">◈</span><div><b>Privacy-aware voting</b><p>Admin views aggregate participation.</p></div></div>
      <div><span className="feature-icon orange">◉</span><div><b>Live election room</b><p>Participation updates in real time.</p></div></div>
    </section>

    <section className="section-block">
      <div className="section-heading"><div><span className="eyebrow">BUILT FOR STUDENT ELECTIONS</span><h2>Everything you need to run a digital election.</h2></div><p>VoteHub brings registration, verification, voting and aggregate results into one clean workflow.</p></div>
      <div className="feature-grid">
        <Feature number="01" title="Register once" text="Create your voter account with a unique Voter ID, date of birth and protected identity data."/>
        <Feature number="02" title="Verify with OTP" text="A six-digit demo OTP verifies the account before login and voting access is granted."/>
        <Feature number="03" title="Cast your ballot" text="Select one candidate in an active election and confirm your vote."/>
        <Feature number="04" title="View participation" text="See election status, vote totals and published aggregate results without exposing voter choices."/>
      </div>
    </section>

    <section className="role-section"><div><span className="eyebrow">TWO PORTALS</span><h2>One platform. Different responsibilities.</h2><p>Voters get a focused voting experience while administrators manage elections and monitor aggregate activity.</p></div><div className="role-cards"><button onClick={() => go('voter-register')}><span>◉</span><div><b>Voter Portal</b><small>Register · Verify · Vote · History</small></div><strong>→</strong></button><button onClick={() => go('admin-register')}><span>◆</span><div><b>Admin Portal</b><small>Create · Monitor · Publish results</small></div><strong>→</strong></button></div></section>
  </>;
}

function Feature({number,title,text}) { return <div className="feature-card"><span>{number}</span><h3>{title}</h3><p>{text}</p><a>Learn more <b>→</b></a></div>; }

function Register({ role, go, setPending, notify }) {
  const [f,setF]=useState({}); const [busy,setBusy]=useState(false);
  const update=(n,v)=>setF(x=>({...x,[n]:v}));
  const submit=async e=>{e.preventDefault(); if(f.password!==f.confirmPassword)return notify('Passwords do not match.','error'); setBusy(true); try{const r=role==='VOTER'?await api.post('/auth/register/voter',f):await api.post('/auth/register/admin',f);setPending({id:role==='VOTER'?f.voterId:f.employeeId,demoOtp:r.data.demoOtp});notify('Account created. Verify your OTP.');go('verify');}catch(x){notify(errorText(x),'error')}finally{setBusy(false)}};
  return <AuthLayout title={role==='VOTER'?'Create your voter account':'Create your admin account'} subtitle={role==='VOTER'?'Register securely to access active college elections.':'Create an administrator account for election management.'} badge={role==='VOTER'?'VOTER REGISTRATION':'ADMIN REGISTRATION'}>
    <form className="auth-form" onSubmit={submit}>
      <div className="form-grid">{role==='VOTER'?<><Field label="Voter ID" value={f.voterId} onChange={v=>update('voterId',v)} placeholder="e.g. VTR2026001"/><Field label="Full name" value={f.fullName} onChange={v=>update('fullName',v)} placeholder="Your full name"/><Field label="Date of birth" type="date" value={f.dob} onChange={v=>update('dob',v)}/><Field label="Aadhaar number" value={f.aadhaar} onChange={v=>update('aadhaar',v)} placeholder="12-digit number" maxLength="12"/></>:<><Field label="Employee ID" value={f.employeeId} onChange={v=>update('employeeId',v)} placeholder="e.g. EMP2026001"/><Field label="Full name" value={f.fullName} onChange={v=>update('fullName',v)} placeholder="Your full name"/></>}<Field label={role==='ADMIN'?'Official email':'Email address'} type="email" value={f.email} onChange={v=>update('email',v)} placeholder="name@example.com"/><Field label="Mobile number" value={f.mobile} onChange={v=>update('mobile',v)} placeholder="10-digit mobile"/><Field label="Password" type="password" value={f.password} onChange={v=>update('password',v)} placeholder="Create a strong password"/><Field label="Confirm password" type="password" value={f.confirmPassword} onChange={v=>update('confirmPassword',v)} placeholder="Repeat password"/></div>
      <label className="check-row"><input type="checkbox" required/> <span>I agree to the VoteHub terms and understand this is a college/demo voting system.</span></label>
      <button className="full-cta" disabled={busy}>{busy?'Creating account…':'Create account'} <span>→</span></button>
    </form>
    <div className="auth-foot">Already verified? <button onClick={()=>go('login')}>Sign in</button></div>
  </AuthLayout>;
}

function AuthLayout({title,subtitle,badge,children}){return <section className="auth-page"><div className="auth-brand-panel"><span className="eyebrow">VOTEHUB</span><h1>Secure access<br/>starts here.</h1><p>Verified identity, protected sessions and a clear path from registration to ballot.</p><div className="auth-points"><span>✓ OTP verification</span><span>✓ Secure password storage</span><span>✓ One-vote enforcement</span></div></div><div className="auth-box"><span className="eyebrow">{badge}</span><h2>{title}</h2><p>{subtitle}</p>{children}</div></section>}
function Field({label,type='text',value,onChange,placeholder,maxLength}){return <label className="field"><span>{label}</span><input required type={type} value={value||''} onChange={e=>onChange(e.target.value)} placeholder={placeholder} maxLength={maxLength}/></label>}

function Verify({pending,go,notify}){const[o,setO]=useState('');const[busy,setBusy]=useState(false);const submit=async e=>{e.preventDefault();setBusy(true);try{await api.post('/auth/verify',{id:pending?.id,otp:o});notify('Account verified successfully. You can now sign in.');go('login')}catch(x){notify(errorText(x),'error')}finally{setBusy(false)}};return <AuthLayout title="Verify your account" subtitle="Enter the six-digit OTP generated for your registration." badge="OTP VERIFICATION"><div className="otp-card"><div className="otp-lock">⌁</div><b>Demo verification code</b><strong>{pending?.demoOtp || '------'}</strong><small>Simulated OTP — no real Aadhaar/UIDAI SMS is sent.</small></div><form onSubmit={submit} className="otp-form"><input autoFocus inputMode="numeric" maxLength="6" value={o} onChange={e=>setO(e.target.value.replace(/\D/g,''))} placeholder="000000"/><button className="full-cta" disabled={busy || o.length!==6}>{busy?'Verifying…':'Verify OTP'} <span>→</span></button></form><div className="auth-foot"><button onClick={()=>go('home')}>← Back to home</button></div></AuthLayout>}

function Login({setUser,go,notify}){const[id,setId]=useState(''),[password,setPassword]=useState(''),[busy,setBusy]=useState(false);const submit=async e=>{e.preventDefault();setBusy(true);try{const r=await api.post('/auth/login',{id,password});localStorage.setItem('token',r.data.token);localStorage.setItem('user',JSON.stringify(r.data));setUser(r.data);notify('Welcome back, '+r.data.name+'!');go(r.data.role==='ADMIN'?'admin':'dashboard')}catch(x){notify(errorText(x),'error')}finally{setBusy(false)}};return <AuthLayout title="Welcome back" subtitle="Sign in with the account you created on VoteHub." badge="SECURE SIGN IN"><form className="auth-form login-form" onSubmit={submit}><Field label="Voter ID / Employee ID" value={id} onChange={setId} placeholder="Enter your ID"/><Field label="Password" type="password" value={password} onChange={setPassword} placeholder="Enter your password"/><button className="full-cta" disabled={busy}>{busy?'Signing in…':'Sign in'} <span>→</span></button></form><div className="auth-foot">New here? <button onClick={()=>go('voter-register')}>Create voter account</button></div></AuthLayout>}

function Dashboard({user,go,setElectionId}){const[elections,setElections]=useState([]);useEffect(()=>{api.get('/elections').then(r=>setElections(r.data)).catch(()=>{})},[]);const active=elections.filter(e=>e.status==='ACTIVE'||e.status==='CLOSING_SOON');return <section className="dashboard-page"><SideNav user={user} page="dashboard" go={go}/><div className="content-panel"><PageIntro eyebrow="VOTER DASHBOARD" title={`Good to see you, ${user?.name?.split(' ')[0] || 'Voter'}.`} text="Your verified voting space. Stay informed and cast your ballot when an election is active." action={<button className="cta small" onClick={()=>go('elections')}>Browse elections →</button>}/><div className="stats-row"><Stat icon="◉" label="Account" value="Verified" tone="green"/><Stat icon="◌" label="Active elections" value={active.length} tone="blue"/><Stat icon="◈" label="Voting privacy" value="Protected" tone="purple"/></div><div className="dashboard-grid"><div className="panel-card"><div className="panel-title"><div><span className="eyebrow">LIVE NOW</span><h3>Active elections</h3></div><button className="text-btn" onClick={()=>go('elections')}>View all →</button></div>{active.length?<div className="compact-list">{active.map(e=><ElectionMini key={e.id} e={e} open={()=>{setElectionId(e.id);localStorage.setItem('electionId',e.id);go('room')}}/> )}</div>:<Empty text="There are no active elections right now."/>}</div><div className="panel-card accent-panel"><span className="eyebrow">YOUR ACCOUNT</span><h3>Verified and ready</h3><p>Your account passed OTP verification. You can participate in elections where you meet the configured eligibility rules.</p><div className="security-lines"><span>✓ Identity verified</span><span>✓ Password protected</span><span>✓ One-vote protection</span></div><button className="outline-btn full" onClick={()=>go('profile')}>View profile</button></div></div></div></section>}

function Stat({icon,label,value,tone}){return <div className="dash-stat"><span className={`stat-icon ${tone}`}>{icon}</span><div><small>{label}</small><b>{value}</b></div></div>}
function ElectionMini({e,open}){return <div className="election-mini"><div className="election-symbol">✓</div><div><b>{e.name}</b><small>{prettyStatus(e.status)} · Ends {formatDate(e.endTime)}</small></div><button onClick={open}>Open →</button></div>}
function Empty({text}){return <div className="empty"><span>○</span><p>{text}</p></div>}

function Elections({user,go,setElectionId}){const[data,setData]=useState([]),[filter,setFilter]=useState('ALL'),[q,setQ]=useState('');useEffect(()=>{api.get('/elections').then(r=>setData(r.data)).catch(()=>{})},[]);const filtered=useMemo(()=>data.filter(e=>(filter==='ALL'||e.status===filter)&&e.name.toLowerCase().includes(q.toLowerCase())),[data,filter,q]);return <section className="list-page"><PageIntro eyebrow="ELECTION CENTER" title="Explore elections" text="Browse upcoming, active and completed college elections."/><div className="toolbar"><div className="searchbox">⌕<input value={q} onChange={e=>setQ(e.target.value)} placeholder="Search elections…"/></div><div className="filters">{['ALL','UPCOMING','ACTIVE','CLOSING_SOON','CLOSED'].map(x=><button key={x} className={filter===x?'selected':''} onClick={()=>setFilter(x)}>{x==='ALL'?'All':prettyStatus(x)}</button>)}</div></div><div className="election-grid">{filtered.map(e=><ElectionCard key={e.id} e={e} open={()=>{setElectionId(e.id);localStorage.setItem('electionId',e.id);go(user?'room':'login')}}/>)}{!filtered.length&&<Empty text="No elections match your search."/>}</div></section>}
function ElectionCard({e,open}){return <article className="election-card"><div className="election-card-head"><span className={`status-pill ${e.status}`}><i/> {prettyStatus(e.status)}</span><span className="year-chip">2026</span></div><div className="election-emblem">✓</div><h3>{e.name}</h3><p>{e.description || 'Election information and candidate details.'}</p><div className="card-meta"><span>◷ <b>Ends</b> {formatDate(e.endTime)}</span><span>◉ <b>Min age</b> {e.minAge}</span></div><button className="card-action" onClick={open}>{e.status==='CLOSED'?'View results':'Open election room'} <span>→</span></button></article>}

function Room({electionId,go,notify}){const[e,setE]=useState(null),[selected,setSelected]=useState(null),[busy,setBusy]=useState(false),[msg,setMsg]=useState('');const load=()=>api.get(`/elections/${electionId}/room`).then(r=>{setE(r.data);setMsg('')}).catch(x=>setMsg(errorText(x)));useEffect(()=>{if(electionId)load();const t=setInterval(()=>electionId&&load(),5000);return()=>clearInterval(t)},[electionId]);const vote=async()=>{if(!selected)return;setBusy(true);try{const r=await api.post(`/elections/${electionId}/vote?candidateId=${selected}`);notify(r.data.message);load()}catch(x){notify(errorText(x),'error')}finally{setBusy(false)}};if(!electionId)return <Empty text="Choose an election first."/>;if(!e)return <div className="loading-card"><div className="spinner"/><b>{msg||'Loading election room…'}</b><button className="text-btn" onClick={()=>go('elections')}>Back to elections</button></div>;return <section className="room-page"><button className="back-btn" onClick={()=>go('elections')}>← Elections</button><div className="room-hero"><div><div className="eyebrow"><span className="pulse-dot"/> LIVE ELECTION ROOM</div><h1>{e.election.name}</h1><p>{e.election.description}</p></div><div className="room-status"><span className={`status-pill ${e.election.status}`}><i/> {prettyStatus(e.election.status)}</span><small>Updated automatically</small></div></div><div className="room-stats"><Stat icon="◉" label="Votes cast" value={e.votes} tone="blue"/><Stat icon="✓" label="Eligibility" value={e.eligible?'Eligible':'Not eligible'} tone={e.eligible?'green':'orange'}/><Stat icon="◌" label="Your ballot" value={e.alreadyVoted?'Submitted':'Not submitted'} tone={e.alreadyVoted?'green':'purple'}/></div><div className="ballot-panel"><div className="panel-title"><div><span className="eyebrow">YOUR BALLOT</span><h2>Select one candidate</h2></div><span className="privacy-note">◈ Private ballot</span></div><div className="candidate-grid">{e.candidates.map(c=><label className={`candidate-card ${selected===c.id?'selected':''}`} key={c.id}><input type="radio" name="candidate" disabled={!e.eligible||e.alreadyVoted} checked={selected===c.id} onChange={()=>setSelected(c.id)}/><div className="candidate-avatar">{initials(c.name)}</div><div className="candidate-info"><h3>{c.name}</h3><span>{c.position}</span><p>{c.description}</p></div><span className="radio-dot"/></label>)}</div>{e.alreadyVoted?<div className="submitted-banner">✓ Your vote has already been recorded for this election.</div>:e.eligible?<button className="full-cta ballot-btn" disabled={!selected||busy} onClick={vote}>{busy?'Recording vote…':'Confirm and record vote'} <span>→</span></button>:<div className="warning-banner">Your account does not currently meet this election's eligibility requirements.</div>}</div><div className="privacy-banner"><span>◈</span><div><b>Privacy-aware ballot</b><p>The voting interface does not display your candidate choice in your voting history. Administration views aggregate participation and results.</p></div></div></section>}

function History({go}){const[data,setData]=useState([]),[loading,setLoading]=useState(true);useEffect(()=>{api.get('/user/history').then(r=>setData(r.data)).catch(()=>{}).finally(()=>setLoading(false))},[]);return <section className="list-page"><PageIntro eyebrow="VOTING HISTORY" title="Your participation" text="A simple record of which elections you participated in—without showing your candidate selection."/><div className="history-card">{loading?<div className="loading-line">Loading history…</div>:data.length?data.map((x,i)=><div className="history-row" key={i}><div className="history-icon">✓</div><div><b>{x.election}</b><small>{x.date ? formatDate(x.date) : 'Election record'}</small></div><span className={x.status==='VOTED'?'history-voted':'history-not'}>{x.status==='VOTED'?'Voted':'Not voted'}</span></div>):<Empty text="No election history yet."/>}</div><button className="outline-btn" onClick={()=>go('elections')}>Browse elections</button></section>}

function Profile({user,go}){return <section className="list-page"><PageIntro eyebrow="PROFILE & SECURITY" title="Your account" text="Review your identity and the security controls protecting your VoteHub access."/><div className="profile-grid"><div className="profile-card"><div className="profile-avatar">{initials(user.name)}</div><h2>{user.name}</h2><span className="role-label">{user.role}</span><div className="profile-details"><Row label="Account status" value="Verified"/><Row label="Email" value={user.email || '—'}/><Row label="Mobile" value={user.mobile || '—'}/><Row label={user.role==='ADMIN'?'Employee ID':'Voter ID'} value={user.role==='ADMIN'?user.employeeId:user.voterId}/></div></div><div className="security-card"><span className="eyebrow">SECURITY CENTER</span><h3>Protection status</h3><Security label="OTP verification" text="Account verification completed"/><Security label="Password" text="Password-protected account"/><Security label="Duplicate voting" text="One-vote authorization enforced"/><Security label="Ballot privacy" text="Candidate choice is not shown in history"/><button className="outline-btn full" onClick={()=>go('history')}>View voting history</button></div></div></section>}
function Row({label,value}){return <div className="profile-row"><span>{label}</span><b>{value}</b></div>}
function Security({label,text}){return <div className="security-item"><span>✓</span><div><b>{label}</b><small>{text}</small></div></div>}

function Admin({user,go,setElectionId}){const[stats,setStats]=useState(null),[elections,setElections]=useState([]);const load=()=>{api.get('/admin/stats').then(r=>setStats(r.data)).catch(()=>{});api.get('/elections').then(r=>setElections(r.data)).catch(()=>{})};useEffect(load,[]);return <section className="dashboard-page"><SideNav user={user} page="admin" go={go}/><div className="content-panel"><PageIntro eyebrow="ADMIN CONSOLE" title="Election control center" text="Manage your election environment and monitor aggregate activity." action={<button className="cta small" onClick={()=>go('elections')}>Monitor live rooms →</button>}/><div className="stats-row admin-stats">{stats&&Object.entries(stats).map(([k,v],i)=><Stat key={k} icon={['◉','◆','▣','●'][i%4]} label={k} value={v} tone={['blue','purple','green','orange'][i%4]}/>)}</div><div className="dashboard-grid"><div className="panel-card"><div className="panel-title"><div><span className="eyebrow">ELECTIONS</span><h3>Current election list</h3></div><button className="text-btn" onClick={()=>go('elections')}>Open center →</button></div><div className="compact-list">{elections.map(e=><ElectionMini key={e.id} e={e} open={()=>{setElectionId(e.id);localStorage.setItem('electionId',e.id);go('room')}}/>)}</div></div><div className="panel-card accent-panel"><span className="eyebrow">PRIVACY MODEL</span><h3>Aggregate monitoring</h3><p>Admin monitoring exposes election status, participation totals and aggregate results rather than a voter-to-candidate mapping.</p><div className="security-lines"><span>✓ Election status</span><span>✓ Candidate totals</span><span>✓ Participation counts</span><span>✓ Audit-ready activity</span></div></div></div></div></section>}
function SideNav({user,page,go}){return <aside className="side-nav"><div className="side-user"><div className="side-avatar">{initials(user?.name)}</div><div><b>{user?.name}</b><small>{user?.role==='ADMIN'?'Administrator':'Verified voter'}</small></div></div><div className="side-links"><button className={page==='dashboard'?'current':''} onClick={()=>go('dashboard')}>⌂ <span>Dashboard</span></button><button onClick={()=>go('elections')}>◉ <span>Live elections</span></button><button className={page==='history'?'current':''} onClick={()=>go('history')}>◷ <span>Voting history</span></button><button className={page==='profile'?'current':''} onClick={()=>go('profile')}>○ <span>Profile & security</span></button></div><div className="side-note"><b>Privacy first</b><p>Your candidate selection is not shown in your history.</p></div></aside>}
function PageIntro({eyebrow,title,text,action}){return <div className="page-intro"><div><span className="eyebrow">{eyebrow}</span><h1>{title}</h1><p>{text}</p></div>{action}</div>}
function Footer(){return <footer><div><b>Vote<span>Hub</span></b><p>Secure digital voting for college elections.</p></div><span>College / demo system · Privacy-aware by design</span></footer>}

createRoot(document.getElementById('root')).render(<App/>);
