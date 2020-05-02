import React, { Component } from "react";
import styles from './LoginForm.module.scss';

export default class LoginForm extends Component {

    handleSubmit = (event) => {
        event.preventDefault()
        console.log(event.target.elements.email.value);
        console.log(event.target.elements.password.value);
    }

    render() {
        return (
            <div className={styles.authWrapper}><div className={styles.authInner}>
                <form onSubmit={this.handleSubmit}>
                    <h2>Sign In</h2>
                    <div className="form-group">
                        <label>Email address</label>
                        <input type="email" id="email" required className="form-control" placeholder="Enter email" />
                    </div>
                    <div className="form-group">
                        <label>Password</label>
                        <input type="password" id="password" required className="form-control" placeholder="Enter password" />
                    </div>
                    <div className="form-group">
                        <div className="custom-control custom-checkbox">
                            <input type="checkbox" className="custom-control-input" id="customCheck1" />
                            <label className="custom-control-label" htmlFor="customCheck1">Remember me</label>
                        </div>
                    </div>
                    <button type="submit" className="btn btn-primary btn-block">Submit</button>
                    <p className={styles.register}>
                        Need to <a href="/register">register?</a>
                    </p>
                </form>
            </div></div>
        );
    }
}