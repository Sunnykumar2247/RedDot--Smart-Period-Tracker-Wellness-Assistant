module.exports = {
  testDir: './tests',
  use: {
    baseURL: 'http://localhost:3000',
    headless: true,
    screenshot: 'only-on-failure',
    video: 'retain-on-failure'
  },
  projects: [
    {
      name: 'chromium',
      use: { ...require('@playwright/test').devices['Desktop Chrome'] }
    }
  ],
  webServer: {
    command: 'npm start',
    url: 'http://localhost:3000',
    timeout: 120000,
    reuseExistingServer: !process.env.CI
  }
};

