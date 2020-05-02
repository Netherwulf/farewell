import React, { Component } from "react";
import styles from './SignupForm.module.scss';

export default class SignupForm extends Component {

    handleSubmit = (event) => {
        event.preventDefault()
        console.log(event.target.elements.firstname.value);
        console.log(event.target.elements.lastname.value);
        console.log(event.target.elements.email.value);
        console.log(event.target.elements.password.value);
    }

    render() {
        return (
            <div className={styles.authWrapper}><div className={styles.authInner}>
            <form>
                <h2>Register</h2>
                <div className="form-group">
                    <label>First name</label>
                    <input type="text" id="firstname" required className="form-control" placeholder="First name" />
                </div>
                <div className="form-group">
                    <label>Last name</label>
                    <input type="text" id="lastname" required className="form-control" placeholder="Last name" />
                </div>
                <div className="form-group">
                    <label>Email address</label>
                    <input type="email" id="email" required className="form-control" placeholder="Enter email" />
                </div>
                <div className="form-group">
                    <label>Password</label>
                    <input type="password" id="password" required className="form-control" placeholder="Enter password" />
                </div>
                <button type="submit" className="btn btn-primary btn-block">Sign Up</button>
                <p className={styles.register}>
                   Would you rather <a href="/login">sign in?</a>
                </p>
            </form>
            </div></div>
        );
    }
}