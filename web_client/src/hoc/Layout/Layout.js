import React, { Component } from 'react';
import { connect } from 'react-redux';
import AppHeader from '../../components/AppHeader/AppHeader';


class Layout extends Component {
    render() {
        const header = this.props.isAuthenticated ? (
            <AppHeader />
        ) : <AppHeader />;

        return (
            <div>
                {header}
                <main>
                    {this.props.children}
                </main>
            </div>
        )
    }
}


const mapStateToProps = state => {
    return {
        isAuthenticated: state.auth.isAuthenticated,
        isIntro: state.auth.isIntro
    }
}

export default connect(mapStateToProps)(Layout);