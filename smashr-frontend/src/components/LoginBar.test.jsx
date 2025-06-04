import { expect, test, vi } from "vitest";
import { render, screen, fireEvent, waitFor } from "@testing-library/react";
import LoginBar from "./LoginBar";
import * as loginService from "../services/loginService";

vi.mock("../services/loginService");

describe("LoginBar component", () => {
  beforeEach(() => {
    vi.clearAllMocks();
  });

  test("renders login form initially", () => {
    render(<LoginBar />);
    expect(screen.getByPlaceholderText(/username/i)).toBeInTheDocument();
    expect(screen.getByPlaceholderText(/password/i)).toBeInTheDocument();
    expect(screen.getByText(/login/i)).toBeInTheDocument();
  });

  test("calls login and shows welcome message on successful login", async () => {
    // Arrange mock implementation
    loginService.default.mockResolvedValue({
      id: 1,
      username: "testuser",
    });

    render(<LoginBar />);

    fireEvent.change(screen.getByPlaceholderText(/username/i), {
      target: { value: "testuser" },
    });

    fireEvent.change(screen.getByPlaceholderText(/password/i), {
      target: { value: "password123" },
    });

    fireEvent.click(screen.getByText(/login/i));

    // Assert: wait for login state change
    await waitFor(() =>
      expect(screen.getByText(/welcome, testuser/i)).toBeInTheDocument()
    );

    expect(loginService.default).toHaveBeenCalledWith(
      "testuser",
      "password123"
    );
  });

  test("logs out when logout button is clicked", async () => {
    loginService.default.mockResolvedValue({
      id: 2,
      username: "admin",
    });

    render(<LoginBar />);

    fireEvent.change(screen.getByPlaceholderText(/username/i), {
      target: { value: "admin" },
    });

    fireEvent.change(screen.getByPlaceholderText(/password/i), {
      target: { value: "secret" },
    });

    fireEvent.click(screen.getByText(/login/i));

    await waitFor(() =>
      expect(screen.getByText(/welcome, admin/i)).toBeInTheDocument()
    );

    fireEvent.click(screen.getByText(/logout/i));

    expect(screen.getByPlaceholderText(/username/i)).toBeInTheDocument();
    expect(screen.queryByText(/welcome/i)).not.toBeInTheDocument();
  });
});
