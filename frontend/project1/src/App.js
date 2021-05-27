import './App.css';
import Login from './Components/Employee/Login';
import React, {useEffect, useState} from 'react';
import ManagerLogin from './Components/Manager/ManagerLogin';
import logo from './imgs/logo.png';
import ForgotPassword from "./Components/ForgotPassword";

//Login Page
function App() {

    const [portal, setPortal] = useState("NA");
    // setPortal("NA")
    // onClick={setPortal("Employee")}
    // onClick={setPortal("Manager")}

    React.useEffect(()=>{
        const getPortal = localStorage.getItem("portal");
        setPortal(getPortal);
        if(getPortal!="NA"){
            setPortal(getPortal);
        }
    },[]);


    React.useEffect(()=>{
        localStorage.setItem("portal", portal)
    }, [portal]);


    function managerPortal(){
        setPortal("Manager");
    }

    function employeePortal(){
        setPortal("Employee");
    }
    console.log(portal);

    function forgotPortal() {
        setPortal("ForgotPassword")
    }

    if(portal==="Manager"){
        return(
            <div className="App">
                <ManagerLogin setPortal={setPortal}/>
            </div>
        );

    }

    else if(portal === "Employee"){
        return(
            <div className="App">
                <Login setPortal={setPortal}/>
            </div>
        );
    }

    else if(portal==="ForgotPassword"){
        return (<div>
            <ForgotPassword setPortal={setPortal}/>
        </div>);
    }

    else{
        return (
            <div className="App">
                <img src={logo} alt=""/>
                <div>
                    <button className="menu-button" onClick={employeePortal}>Employee Portal</button>
                    <button className="menu-button" onClick={managerPortal}>Manager Portal</button>
                    <button className="menu-button" onClick={forgotPortal}>Forgot Password</button>
                </div>

            </div>
        )
    }

}

export default App;

// <div className="App">
//     <Route {...rest} render={(props) => (
//         loggedIn === true
//             ? <EmployeeHome {...props} />
//             : <Redirect to='/login' />
//     )} />
// </div>