import React, { Component } from "react";
import styles from './SignupForm.module.scss';
import { registerUser } from 'logic/userLogic';
import { withRouter } from 'react-router-dom';
import Snackbar from '@material-ui/core/Snackbar';
import Alert from '@material-ui/lab/Alert';

class SignupForm extends Component {

    state = {
        successOpen: false,
        successMessage: '',
        errorOpen: false,
        errorMessage: ''
    }

    handleSubmit = (event) => {
        event.preventDefault();
        const userData = {
            firstName: event.target.elements.firstName.value,
            lastName: event.target.elements.lastName.value,
            email: event.target.elements.email.value,
            password: event.target.elements.password.value,
            type: event.target.elements.type.checked ? "EMPLOYEE" : "USER"
        }
        this.handleRegistration(userData);
    }

    handleRegistration = async (userData) => {
        const result = await registerUser(userData);
        if (result) {
            if (result.isError)
                this.setState({ errorMessage: result.errorMessage, errorOpen: true });
            else
                this.setState({ successMessage: "Account created successfully", successOpen: true });
        }
    }

    handleClose = (event, reason) => {
        if (this.state.errorOpen)
            this.setState({ errorOpen: false });
        else {
            this.setState({ successOpen: false },
            this.props.history.push("/login"));
        }
    }

    render() {
        return (
            <>
            <Snackbar anchorOrigin={{ vertical: 'top', horizontal: 'right' }} open={this.state.successOpen} autoHideDuration={3000} onClose={this.handleClose}>
                <Alert onClose={this.handleClose} severity="success">
                    {this.state.successMessage}
                </Alert>
            </Snackbar>
            <Snackbar anchorOrigin={{ vertical: 'top', horizontal: 'right' }} open={this.state.errorOpen} autoHideDuration={3000} onClose={this.handleClose}>
                <Alert onClose={this.handleClose} severity="error">
                    {this.state.errorMessage}
                </Alert>
            </Snackbar>
            <div className={styles.authWrapper}><div className={styles.authInner}>
            <form onSubmit={this.handleSubmit}>
                <h2>Register</h2>
                <div className="form-group">
                    <label>First name</label>
                    <input type="text" id="firstName" required className="form-control" placeholder="First name" />
                </div>
                <div className="form-group">
                    <label>Last name</label>
                    <input type="text" id="lastName" required className="form-control" placeholder="Last name" />
                </div>
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
                        <input type="checkbox" className="custom-control-input" id="type" />
                        <label className="custom-control-label" htmlFor="type">Worker Account (needs to be activated)</label>
                    </div>
                </div>
                <button type="submit" className="btn btn-primary btn-block">Sign Up</button>
                <p className={styles.register}>
                   Would you rather <a href="/login">sign in?</a>
                </p>
            </form>
            </div></div>
            </>
        );
    }
}

export default withRouter(SignupForm);