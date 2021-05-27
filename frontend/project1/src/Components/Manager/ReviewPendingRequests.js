import React, {useState} from 'react';
import axios from "axios";
import emailjs from "emailjs-com";
import {NotificationManager} from "react-notifications";
function ReviewPendingRequests(props){
    const [data, setData] = useState([]);
    const [selectItem, setSelectItem]= useState("");
    const [action, setAction]= useState("");
    const [update, setUpdate]= useState(false);
    const [name, setName] = useState("*");

    function back() {
        props.setNav("NA")
    }

    React.useEffect(()=>{
        // console.log("code was here");
        query(name);
    },[])

    React.useEffect(()=>{
        if(name.length===0){setName("*")}
        query(name);
    },[name]);

    React.useEffect(()=>{
        console.log("updated data");
        setUpdate(false)
        setName("*")
        query(name)
    },[update])

    React.useEffect(()=>{
        console.log(selectItem)
        let id = selectItem.id;
        let title = selectItem.title;
        let username = selectItem.username;
        let amount = selectItem.amount;
        let date = selectItem.date;

        let admin = props.admin;
        console.log(date);

        let setDate = new Date(date);
        date = getFormattedDate(setDate);

            // String id, String username, String title, Double amount, String date, boolean approved
        if(action==="approve"){
            let approved = "approved";
            approval(id, title, username, amount, date, approved, admin).then(r => {
                //go ahead and approve the item
                NotificationManager.success('Successful', 'Approved!');
                setUpdate(true);

                //remove the item and then rerender the page
            });

        }
        else if(action==="deny"){
            //go ahead and deny the reimbursement
            let approved = "denied";
            //go ahead and approve the item
            denial(id, title, username, amount, date, approved, admin).then(r => {
                //remove the item and then rerender the page
                NotificationManager.success('Successful', 'Denied!');
                setUpdate(true);
            });
        }
        else{
            setSelectItem("")
        }
    },[selectItem]);

    async function approval(id, title, username, amount, date, approved, approvedBy){
        await axios({
            method: 'post',
            url: "http://localhost:9999/approve",
            data: {id, title, username, amount, date, approved, approvedBy},
            headers : {
                'Content-Type': 'application/json'
            }
        }).then(res => {
            if(res.status===200){
                getEmail("approved", username, title);
                //SEND EMAIL TO EMPLOYEE
            }
            // if(res.status===401)
            else{
                console.log("here");
                alert("user not found");
            }
        })
            .catch(err => console.log(err));
    }

    async function denial(id, title, username, amount, date, approved, approvedBy){
        await axios({
            method: 'post',
            url: "http://localhost:9999/deny",
            data: {id, title, username, amount, date, approved, approvedBy},
            headers : {
                'Content-Type': 'application/json'
            }
        }).then(res => {
            if(res.status===200){
                getEmail("denied", username, title);
                //SEND EMAIL TO EMPLOYEE
            }
            // if(res.status===401)
            else{
                console.log("here");
                alert("user not found");
            }
        })
            .catch(err => console.log(err));
    }


    function sendEmail(status, email, username, title){

        let company = "Golden Gates Bank";
        let messageBody = "Hello, your application for a reimbursement for \"" + title +"\" was "+ status+".";
        let message={
            "to_name": "",
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


    async function getEmail(status,username, title){
        await axios({
            method: 'get',
            url: "http://localhost:9999/getEmail",
            params: {username},
            headers : {
                'Content-Type': 'application/json'
            }
        }).then(res => {
            if(res.status===200){
                let email= res.data;
                sendEmail(status, email, username, title)
            }
            else{
                alert("could not retrieve an email")
            }
        })
            .catch(err => console.log(err));
    }

    async function query(name){
        await axios({
            method: 'get',
            url: "http://localhost:9999/viewAllPending",
            params:{name},
            headers : {
                'Content-Type': 'application/json'
            }
        }).then(res => {
            if(res.status===200){
                console.log(res.data);
                // setData(res.data);
                // console.log(data.entries());
                setData(res.data);
            }
            // if(res.status===401)
            else{
                console.log("here");
                alert("nothing found");
            }
        })
            .catch(err => console.log(err));

    }

    function getFormattedDate(date) {
        let year = date.getFullYear();

        let month = (1 + date.getMonth()).toString();
        month = month.length > 1 ? month : '0' + month;

        let day = date.getDate().toString();
        day = day.length > 1 ? day : '0' + day;

        return  year+ '-' + month + '-' +day ;
    }

    return (
        <div >
            <h3>Review Pending Reimbursement Request</h3>
            <button onClick={back}>X</button>

            <div className="view">
                <div className="filter">
                    <h5 >Filter by username:</h5>
                    <input type="text" onChange={e=>setName(e.target.value)}/>
                </div>
                <div className="scroll">
                    {data.map(d=>{
                        return (
                                <div className="view-pending" key={d.id}> Username : {d.username}; Title: {d.title};
                                    Amount: {d.amount}; Date: {getFormattedDate(new Date(d.date))}
                                    <div className="review-buttons">
                                        <button onClick={()=>{setSelectItem(d);setAction("approve")}} className="approve">Approve</button>
                                        <button onClick={()=>{setSelectItem(d);setAction("deny")}} className="deny">Deny</button>
                                    </div>

                                </div>
                            )
                        })
                    }
                </div>
            </div>
        </div>
    );

}
export default ReviewPendingRequests;