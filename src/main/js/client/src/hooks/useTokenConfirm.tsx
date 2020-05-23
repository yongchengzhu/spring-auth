import { useEffect, useState } from "react";

import server from '../apis/server';
import { RouteComponentProps } from "react-router";

const useTokenConfirm = (props: RouteComponentProps) => {
  const [message, setMessage] = useState('Confirming token, please wait...');

  useEffect(() => {
    const regex: RegExp = /\?token=(?<token>.*)[&?]/;
    const match: RegExpMatchArray | null = props.location.search.match(regex);
    if(match && match.groups) {
      const token:string = match.groups.token;
      server.put(`/user/confirm?token=${token}`)
        .then(() => setMessage('Email confirmation successful, your account is enabled.'))
        .catch((err: Error) => setMessage(err.message));
    } else {
      console.log('Failed: Cannot extract valid token.');
    }
  });

  return [message];
}

export default useTokenConfirm;