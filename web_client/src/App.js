import React from 'react';
import { Redirect, Route, Switch, withRouter } from 'react-router-dom';
import '../node_modules/bootstrap/dist/css/bootstrap.min.css';
import { connect } from 'react-redux';
import CssBaseline from '@material-ui/core/CssBaseline';
import Layout from './hoc/Layout/Layout';
import Funerals from './components/Funerals/Funerals';
import Reservations from './components/Reservations/Reservations';
import Reserve from './components/Reserve/Reserve';
import Landing from './components/Landing/Landing';
import Login from './components/Login/Login';
import Register from './components/Register/Register';
import '../node_modules/bootstrap/dist/css/bootstrap.min.css';
import * as actions from './store/actions/actionsIndex';

class App extends React.Component {

  render() {

    const nonAuthenticatedRoutes = (
      <Switch>
        <Route path='/login' component={Login} />
        <Route path='/register' component={Register} />
        <Route path='/funerals' component={Funerals} />
        <Route path='/reservations' component={Reservations} />
        <Redirect to='/login' />
      </Switch>
    );

    const authenticatedRoutes = (
      <Switch>
        <Route path='/funerals' component={Funerals} />
        <Route path='/reservations' component={Reservations} />
        <Route path='/reserve' component={Reserve} />
        <Route path='/my-reservations' component={Reservations} />
        <Route path='/logout' component={Landing} />
        <Route path='/' exact component={Landing} />
        <Redirect to='/' />
      </Switch>
    );
    return (
      <CssBaseline>
        <Layout>
          { this.props.isAuthenticated ? authenticatedRoutes : nonAuthenticatedRoutes }
        </Layout>
      </CssBaseline>
    );
  }
}

const mapStateToProps = state => ({
  isAuthenticated: state.auth.isAuthenticated
});

const mapDispatchToProps = dispatch => ({
  onLogin: () => dispatch(actions.login()),
});

export default withRouter(connect(mapStateToProps, mapDispatchToProps)(App));
