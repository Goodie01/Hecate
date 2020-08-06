import React from 'react';
import Col from 'react-bootstrap/Col'
import Row from 'react-bootstrap/Row'
import Container from "react-bootstrap/Container";

const List = (props) => {
    const {repos: listeners} = props;
    if (!listeners) return <p>No listeners, sorry</p>;
    else if (listeners.length === 0) return <p>Empty listeners, sorry</p>;
    else return (
        <Container>
            <Row>
                <Col>Id</Col>
                <Col>Port</Col>
                <Col>Http Method</Col>
                <Col>Context path</Col>
            </Row>
            {listeners.map((listener) => {
                return (
                    <Row>
                        <Col>{listener.id}</Col>
                        <Col>{listener.port}</Col>
                        <Col>{listener.httpMethod}</Col>
                        <Col>{listener.context}</Col>
                    </Row>
                );
            })}
        </Container>
    );
};
export default List;