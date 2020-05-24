import React, { Component } from 'react';
import styles from './Graves.module.scss';
import GraveCard from '../GraveCard/GraveCard';
import Grid from '@material-ui/core/Grid';
import { withRouter } from 'react-router-dom';
import * as RestClient from 'api/REST/RestClient';

class Graves extends Component {     

    state = {
        graves: [],
        selectedGraveNumber: null
    }

    toReserve = () => {
        const grave = this.state.graves.find(grave => grave.graveNumber === this.state.selectedGraveNumber);
        this.props.history.push("/reserve-grave", { ...grave });
    }

    componentDidMount() {
        this.getGraves();
    }

    getGraves = async () => {
        const graves = await RestClient.getCemeteryGraves();
        if (graves) {
            this.setState({ graves: graves.filter(grave => grave.userId === null) });
        }
    }

    render() {
        return (
            <div className={styles.container}>
            <div className={styles.graveArea}>
                <Grid
                    container
                    direction="row"
                    justify="center"
                    alignItems="center"
                >
                { this.state.graves.map(grave => 
                    <GraveCard onClick={() => {this.setState({selectedGraveNumber: grave.graveNumber})} } key={grave.graveNumber} isSelected={this.state.selectedGraveNumber === grave.graveNumber} {...grave} />) }
               </Grid>
            </div>
                <div className={styles.buttonArea}>
                    <button type="submit" disabled={this.state.selectedGraveNumber ? false : true} onClick={(this.toReserve)} className="btn btn-dark btn-block">Make a reservation</button>
                </div>     
            </div>
        )
    }
}

export default withRouter(Graves);
