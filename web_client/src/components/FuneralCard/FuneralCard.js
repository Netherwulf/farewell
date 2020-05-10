import React, { Component } from 'react';
import styles from './FuneralCard.module.scss';
import Card from '@material-ui/core/Card';
import CardActions from '@material-ui/core/CardActions';
import CardContent from '@material-ui/core/CardContent';
import Button from '@material-ui/core/Button';
import Typography from '@material-ui/core/Typography';
import Collapse from '@material-ui/core/Collapse';
import moment from 'moment';

class FuneralCard extends Component {

    state = {
        open: false
    }

    handleClick = (event) => {
        this.setState({open: !this.state.open});
    }

    render() {
        const deceased = this.props.grave.deceased.sort((d1, d2) => moment(this.props.date).diff(d2.dateOfDeath, 'days') - moment(this.props.date).diff(d1.dateOfDeath, 'days'))[0];
        return (
            <Card className={styles.root} variant="outlined">
            <CardContent>
                <Typography className={styles.title} color="textSecondary" gutterBottom>
                { moment(this.props.date).format('MMMM Do YYYY, HH:mm') }
                </Typography>
                <Typography variant="h5" component="h2">
                { deceased.name } { deceased.surname }
                </Typography>
                <Typography className={styles.pos} color="textSecondary">
                { moment(deceased.dateOfBirth).format('MMMM Do YYYY') } - { moment(deceased.dateOfDeath).format('MMMM Do YYYY') }
                </Typography>
            </CardContent>
            <CardActions>
                <Button size="small" onClick={this.handleClick}>Details</Button>
            </CardActions>
            <Collapse in={this.state.open} timeout="auto" unmountOnExit>
            <CardContent>
            <Typography>
                Funeral director: {this.props.funeralDirectorId}
            </Typography>
            </CardContent>
            </Collapse>
            </Card>
        )
    }
}

export default FuneralCard;
