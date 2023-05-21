import { test, expect } from '@playwright/test';

const url = "http://localhost:3000";

test('Page loads successfully', async ({ page }) => {
  await page.goto(url);

  // Expect a title "to contain" a substring.
  await expect(page).toHaveTitle(/Safe Contract Changes Demo/);
});

test('Can add a new person', async ({ page}) => {
  await page.goto(url);

  const name = await page.getByLabel('Name');
  await expect(name).toBeEnabled();

  await name.fill('Demo Name');
  const addPersonButton = await page.getByRole('button', {name: "Add Person"});
  await expect(addPersonButton).toBeEnabled();

  await addPersonButton.click();

  const nameConfirm = await page.getByText('Demo Name');
  await expect(nameConfirm).toBeVisible();
});
