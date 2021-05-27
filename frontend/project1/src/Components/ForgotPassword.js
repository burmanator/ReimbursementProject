import React, {useState} from "react";
import axios from "axios";
import emailjs from 'emailjs-com';
import {NotificationContainer, NotificationManager} from "react-notifications";

function ForgotPassword(props){
    const [username, setUsername] = useState("");
    const [email, setEmail] = useState("");
    const [employeeStatus, setEmployeeStatus] = useState("");

    let generator = require('generate-password');

    function back() {
        props.setPortal("NA")
    }


    async function sendPassword(e) {
        e.preventDefault();
        console.log(email)
        console.log(username)
        let password ="";
        let path ="http://localhost:9999/forgot";
        if(employeeStatus==="admin"){
            path = "http://localhost:9999/adminForgot";
        }

        await axios({
            method: 'post',
            url: path,
            data: {username, email, password},
            headers : {
                'Content-Type': 'application/json'
            }
        }).then(res => {
                if(res.status===200){
                    //generate new password and send it to the email
                    NotificationManager.success('A temporary password will be sent to the email if it is in our system.');
                    let password = generator.generate({
                        length: 10,
                        numbers: true
                    });
                    console.log(username+password);
                    //make a post request to update the password to the generated password
                    updatePassword(username, password);
                }

                else{

                }
        })
            .catch(err => console.log(err));
    }

    async function updatePassword(username, password){
        let path = "http://localhost:9999/forgotUpdatePassword";
        if(employeeStatus=="admin"){
            path = "http://localhost:9999/adminForgotUpdatePassword";
        }
        await axios({
            method: 'post',
            url: path,
            params: {username,password},
            headers : {
                'Content-Type': 'application/json'
            }
        }).then(res => {
            sendEmail(username, password, email);
        })
            .catch(err => console.log(err));
    }

    function sendEmail(username, password, email){

        let company = "Golden Gates Bank";
        let messageBody = "Hello, " + username+". Your temporary password, upon your request, is "+ password+
            ".";
        let message={
            "to_name": username,
            "from_name": company,
            "to_email": email,
            "message": messageBody
        }
        console.log("code was here");
        // console.log(message);
        emailjs.send('service_x6l75f7','template_ulr88ue', message,'user_8lya55qZw4lyu8St4iZ74').then((result)=>{
            console.log("sent");
        })
    }


    return(
        <div>
            <NotificationContainer/>
            <form className="forgot-password-form" onSubmit={sendPassword}>
                <button onClick={back}>X</button>
                <div className="forgot-password-form-input">
                    <label>Enter your account username</label>
                    <input type="text" onChange={e=>{setUsername(e.target.value)}}/>
                    <label>Enter your account email</label>
                    <input type="text" onChange={e=>{setEmail(e.target.value)}}/>
                    <select onChange={e=>{setEmployeeStatus(e.target.value)}}>
                        <option value="employee">Employee</option>
                        <option value="admin">Admin</option>
                    </select>
                    <button type="submit">Reset password</button>
                </div>

            </form>


        </div>
    )
}

export default ForgotPassword;