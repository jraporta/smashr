import { Outlet } from 'react-router-dom';
import LoginBar from '../components/LoginBar';

export default function Layout() {
  return (
    <div>
        <LoginBar/>
        <Outlet />
    </div>
  );
}