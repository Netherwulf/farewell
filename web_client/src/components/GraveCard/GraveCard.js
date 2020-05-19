import React, { Component } from 'react';
import styles from './GraveCard.module.scss';
import Card from '@material-ui/core/Card';
import CardContent from '@material-ui/core/CardContent';
import Typography from '@material-ui/core/Typography';
import { shadows } from '@material-ui/system';

class GraveCard extends Component {

    render() {
        return (
            <Card className={styles.root} variant={this.props.isSelected ? "elevation" : "outlined"} onClick={this.props.onClick} >
            <CardContent>
                <Typography className={styles.title} color="textSecondary" gutterBottom>
                { this.props.coordinates }
                </Typography>
                <Typography variant="h5" component="h2">
                Grave { this.props.graveNumber }
                </Typography>
                <Typography className={styles.pos} color="textSecondary">
                Capacity: { this.props.capacity }
                </Typography>
            </CardContent>
            </Card>
        )
    }
}

export default GraveCard;
