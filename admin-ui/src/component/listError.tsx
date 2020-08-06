import React from 'react';


function listError() {
    return function WihLoadingComponent() {
        return (
            <p style={{ textAlign: 'center', fontSize: '30px' }}>
                Something broke
            </p>
        );
    };
}

export default listError;