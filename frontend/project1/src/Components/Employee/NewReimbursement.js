import React, {useState}from "react";
import axios from "axios";
import {NotificationManager} from "react-notifications";

function NewReimbursement(props){

    const [title, setTitle] = useState("");
    const [date, setDate] = useState("");
    const [amount, setAmount] = useState(0);

    let username = props.username;
    function back() {
        props.setNav("NA")
    }

    function clear(){
        setTitle("");
        setDate("");
        setAmount(0);
    }

    function getFormattedDate(date) {
        let year = date.getFullYear();

        let month = (1 + date.getMonth()).toString();
        month = month.length > 1 ? month : '0' + month;

        let day = date.getDate().toString();
        day = day.length > 1 ? day : '0' + day;

        return  year+ '-' + month + '-' +day ;
    }

    async function formSubmit(e) {
        e.preventDefault();
        if(title ===""||title ===undefined||date==null||date==undefined||
            amount<0||amount==undefined||amount==null||title ==null){
            NotificationManager.error('Unsuccessful', 'All fields must be compeleted');
            return;
        }

        let approved = "pending";
        await axios({
            method: 'post',
            url: "http://localhost:9999/newReimbursementRequest",
            data: { username, title, date, amount, approved},
            headers : {
                'Content-Type': 'application/json'
            }
        }).then(res => {
            let status = res.data;
            if(status===200){
                NotificationManager.success('Successful', 'Successfully Submitted the Reimbursement Request!');
            }
            else{
                NotificationManager.error('Unsuccessful', 'Sorry, we couldn\'t change your email');
            }
        })
            .catch(err => alert(err));
    }

    function updateTitle(e) {
        e.preventDefault();
        let title = e.target.value;
        setTitle(title);

    }

    function updateDate(e) {
        e.preventDefault();
        let date = new Date(e.target.value);
        setDate(getFormattedDate(date));
        console.log(date);
    }

    function updateAmount(e) {
        e.preventDefault();
        let amount = (e.target.value).toString();
        setAmount(amount);
    }


    return(
        <div>
            <button className="close-button" onClick={back}>X</button>
            <h3>Submit A New Reimbursement</h3>
            <form onSubmit={formSubmit}>
                <label>Title</label>
                <input name="title" type="text" onChange={updateTitle} placeholder="Padre's game social"/>
                <label>Date</label>
                <input name="date"   onChange={updateDate} type="date"/>
                <label>Amount</label>
                <input name="amount" onChange={updateAmount} type="decimal" placeholder="ex: for $30  enter 30"/>
                <button> Submit</button>
            </form>
        </div>

    );
}

export default NewReimbursement;