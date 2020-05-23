import React from 'react';
import { RouteComponentProps } from 'react-router';

import useTokenConfirm from '../hooks/useTokenConfirm';

const TokenConfirmPage: React.FC<RouteComponentProps> = props => {
  const [message]: any[] = useTokenConfirm(props);
  
  return (
    <div>{message}</div>
  );
};

export default TokenConfirmPage;