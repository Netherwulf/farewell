import React, { Component } from 'react';
import styles from './Register.module.scss';
import SignupForm from './SignupForm';

class Register extends Component {

    render() {
        return (
            <div className={styles.container}>
                <SignupForm/>
            </div>
        )
    }
}

export default Register;
